package dev.s7a.animotion.converter.generator

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
import dev.s7a.animotion.converter.data.animotion.Part
import dev.s7a.animotion.converter.data.blockbench.Animation
import dev.s7a.animotion.converter.data.blockbench.Keyframes
import dev.s7a.animotion.converter.exception.NotFoundPartException
import dev.s7a.animotion.converter.loader.ResourcePack
import dev.s7a.animotion.converter.util.toCamelCase
import dev.s7a.animotion.converter.util.toPascalCase
import java.io.File
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CodeGenerator(
    private val resourcePack: ResourcePack,
) {
    @OptIn(ExperimentalUuidApi::class)
    fun save(parent: File) {
        val animotionClass = ClassName("dev.s7a.animotion", "Animotion")
        val animotionModelClass = ClassName("dev.s7a.animotion", "AnimotionModel")
        val partClass = ClassName("dev.s7a.animotion.model", "Part")
        val animationClass = ClassName("dev.s7a.animotion.model", "Animation")
        val materialClass = ClassName("org.bukkit", "Material")
        val vectorClass = ClassName("org.bukkit.util", "Vector")
        val ktLintRuleEngine = KtLintRuleEngine(StandardRuleSetProvider().getRuleProviders())

        parent.mkdirs()
        resourcePack.animotion.models.forEach { (model, parts) ->
            val className = model.name.toPascalCase()
            val file = parent.resolve("$className.kt")
            val partByUuid = mutableMapOf<Uuid, Part>()

            FileSpec
                .builder(resourcePack.animotion.settings.`package`, className)
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
                                        partClass,
                                    ).initializer(
                                        "part(%L)",
                                        buildList {
                                            // item_model
                                            add(CodeBlock.of("%S", "${resourcePack.animotion.settings.namespace}:${model.name}_$index"))

                                            // material
                                            add(CodeBlock.of("%T.%N", materialClass, resourcePack.animotion.settings.item.bukkit))

                                            // CustomModelData
                                            add(CodeBlock.of("%L", part.customModelData))

                                            val hasPosition = part.origin.any { it != 0.0 }
                                            val hasRotation = part.rotation.any { it != 0.0 }

                                            // position
                                            if (hasPosition || hasRotation) {
                                                add(
                                                    CodeBlock.of(
                                                        "%T(%L, %L, %L)",
                                                        vectorClass,
                                                        *part.origin.map { it / 16.0 }.toTypedArray(),
                                                    ),
                                                )
                                            }

                                            // rotation
                                            if (hasRotation) {
                                                add(CodeBlock.of("%T(%L, %L, %L)", vectorClass, *part.rotation.toTypedArray()))
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
                                        animationClass,
                                    ).initializer(
                                        "$animationFunName(%L, %L)",
                                        animation.length,
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
                                                    "%N to\nlistOf(\n%L)",
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
                                                                "%L to %N(%L, %L, %L)",
                                                                keyframe.time,
                                                                keyframeFunName,
                                                                point.x / div,
                                                                point.y / div,
                                                                point.z / div,
                                                            )
                                                        }.joinToCode(),
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
