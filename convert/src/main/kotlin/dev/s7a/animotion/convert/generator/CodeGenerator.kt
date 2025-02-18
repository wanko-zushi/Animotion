package dev.s7a.animotion.convert.generator

import com.pinterest.ktlint.rule.engine.api.Code
import com.pinterest.ktlint.rule.engine.api.KtLintRuleEngine
import com.pinterest.ktlint.rule.engine.core.api.AutocorrectDecision
import com.pinterest.ktlint.ruleset.standard.StandardRuleSetProvider
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.joinToCode
import dev.s7a.animotion.convert.data.animotion.Part
import dev.s7a.animotion.convert.data.blockbench.Animation
import dev.s7a.animotion.convert.data.blockbench.Keyframes
import dev.s7a.animotion.convert.exception.NotFoundPartException
import dev.s7a.animotion.convert.loader.ResourcePack
import dev.s7a.animotion.convert.util.toCamelCase
import dev.s7a.animotion.convert.util.toPascalCase
import java.io.File
import kotlin.math.roundToLong
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CodeGenerator(
    private val resourcePack: ResourcePack,
) {
    @OptIn(ExperimentalUuidApi::class)
    fun save(parent: File) {
        val animotionClass = ClassName("dev.s7a.animotion", "Animotion")
        val animotionModelClass = ClassName("dev.s7a.animotion", "AnimotionModel")
        val modelPartClass = ClassName("dev.s7a.animotion", "ModelPart")
        val modelAnimationClass = ClassName("dev.s7a.animotion", "ModelAnimation")
        val interpolationClass = ClassName("dev.s7a.animotion.common.BaseAnimation", "Interpolation")
        val vector3Class = ClassName("dev.s7a.animotion.common", "Vector3")
        val ktLintRuleEngine = KtLintRuleEngine(StandardRuleSetProvider().getRuleProviders())

        parent.mkdirs()
        resourcePack.animotion.models.forEach { (model, parts) ->
            val className = model.name.toPascalCase()
            val file = parent.resolve("$className.kt")
            val partByUuid = mutableMapOf<Uuid, Part>()

            FileSpec
                .builder(resourcePack.animotion.settings.packageName, className)
                .addType(
                    TypeSpec
                        .classBuilder(className)
                        .primaryConstructor(
                            FunSpec
                                .constructorBuilder()
                                .addParameter("animotion", animotionClass)
                                .addParameter(ParameterSpec.builder("baseScale", Float::class).defaultValue("%L", "1.0F").build())
                                .build(),
                        ).superclass(animotionModelClass)
                        .addSuperclassConstructorParameter("%N", "animotion")
                        .addSuperclassConstructorParameter("%N", "baseScale")
                        .addProperties(
                            parts.mapIndexed { index, part ->
                                partByUuid[part.uuid] = part

                                PropertySpec
                                    .builder(
                                        part.name.toCamelCase(),
                                        modelPartClass,
                                    ).initializer(
                                        "part(\n%L)",
                                        buildList {
                                            // item_model
                                            add(CodeBlock.of("%S", "${resourcePack.animotion.settings.namespace}:${model.name}_$index"))

                                            // CustomModelData
                                            add(CodeBlock.of("%L", part.customModelData))

                                            val hasPosition = part.origin.any { it != 0.0 }
                                            val hasRotation = part.rotation.any { it != 0.0 }

                                            // position
                                            if (hasPosition || hasRotation) {
                                                add(
                                                    CodeBlock.of(
                                                        "%T(%L, %L, %L)",
                                                        vector3Class,
                                                        *part.origin.map { it / 16.0 }.toTypedArray(),
                                                    ),
                                                )
                                            }

                                            // rotation
                                            if (hasRotation) {
                                                add(CodeBlock.of("%T(%L, %L, %L)", vector3Class, *part.rotation.toTypedArray()))
                                            }
                                        }.joinToCode(),
                                    ).addModifiers(KModifier.PRIVATE)
                                    .build()
                            },
                        ).addProperties(
                            model.animations.map { animation ->
                                val animationFunName =
                                    when (animation.loop) {
                                        Animation.LoopType.Loop -> "loopAnimation"
                                        Animation.LoopType.Once -> "onceAnimation"
                                        Animation.LoopType.Hold -> "holdAnimation"
                                    }

                                PropertySpec
                                    .builder(
                                        animation.name.removePrefix("animation.").toCamelCase(),
                                        modelAnimationClass,
                                    ).initializer(
                                        "$animationFunName(%L, %L)",
                                        (animation.length * 20).roundToLong(), // seconds -> ticks
                                        animation.animators
                                            .flatMap { (uuid, animator) ->
                                                val part = partByUuid[uuid] ?: throw NotFoundPartException(uuid)
                                                listOf(
                                                    part to animator,
                                                    *part.children
                                                        .map {
                                                            it to animator
                                                        }.toTypedArray(),
                                                    // TODO merge & teleport
                                                )
                                            }.sortedBy { (part) ->
                                                part.customModelData
                                            }.map { (part, animator) ->
                                                CodeBlock.of(
                                                    "timeline(%N) {\n%L\n}",
                                                    part.name.toCamelCase(),
                                                    animator.keyframes
                                                        .sortedBy { it.time }
                                                        .map { keyframe ->
                                                            val (keyframeFunName, div) =
                                                                when (keyframe.channel) {
                                                                    Keyframes.Channel.Rotation -> "rotation" to 1.0
                                                                    Keyframes.Channel.Position -> "position" to 16.0
                                                                    Keyframes.Channel.Scale -> "scale" to 1.0
                                                                }

                                                            val point = keyframe.dataPoints[0]
                                                            CodeBlock.of(
                                                                "%N(%L, %L, %L, %L, %T.%L)",
                                                                keyframeFunName,
                                                                (keyframe.time * 20).roundToLong(), // seconds -> ticks
                                                                point.x / div,
                                                                point.y / div,
                                                                point.z / div,
                                                                interpolationClass,
                                                                keyframe.interpolation.name,
                                                            )
                                                        }.joinToCode("\n"),
                                                )
                                            }.joinToCode(),
                                    ).build()
                            },
                        ).build(),
                ).build()
                .toString()
                .let {
                    val code =
                        Code.fromSnippet(
                            // Remove public modifier: https://github.com/square/kotlinpoet/issues/1001
                            it.replace("public class", "class").replace("public val", "val"),
                        )
                    ktLintRuleEngine.format(code) { lintError ->
                        if (lintError.canBeAutoCorrected) {
                            AutocorrectDecision.ALLOW_AUTOCORRECT
                        } else {
                            AutocorrectDecision.NO_AUTOCORRECT
                        }
                    }
                }.let(file::writeText)
        }
    }
}
