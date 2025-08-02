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
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.joinToCode
import dev.s7a.animotion.convert.InputPack
import dev.s7a.animotion.convert.Part
import dev.s7a.animotion.convert.data.BlockbenchModel
import dev.s7a.animotion.convert.exception.NotFoundPartException
import dev.s7a.animotion.convert.util.toCamelCase
import dev.s7a.animotion.convert.util.toPascalCase
import java.io.File
import kotlin.math.roundToLong
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CodeGenerator(
    private val resourcePack: InputPack,
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
        resourcePack.animotion.parts.forEach { (model, parts) ->
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
                                            add(CodeBlock.of("%S", "${resourcePack.animotion.settings.namespace}:${model.name}/$index"))

                                            // CustomModelData
                                            add(CodeBlock.of("%L", part.customModelData))

                                            val hasPosition = part.origin.isZero.not()
                                            val hasRotation = part.rotation.isZero.not()

                                            // position
                                            if (hasPosition || hasRotation) {
                                                add(
                                                    CodeBlock.of(
                                                        "%T(%L, %L, %L)",
                                                        vector3Class,
                                                        *(part.origin / 16.0).toTypedArray(),
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
                        ).run {
                            if (parts.all { it.children.isEmpty() }) {
                                this
                            } else {
                                addInitializerBlock(
                                    buildCodeBlock {
                                        parts.forEach { part ->
                                            val children = part.children
                                            if (children.isNotEmpty()) {
                                                addStatement(
                                                    "%N.children(%L)",
                                                    part.name.toCamelCase(),
                                                    children.joinToCode { CodeBlock.of("%N", it.name.toCamelCase()) },
                                                )
                                            }
                                        }
                                    },
                                )
                            }
                        }.addProperties(
                            model.animations.map { animation ->
                                val animationFunName =
                                    when (animation.loop) {
                                        BlockbenchModel.Animation.LoopType.Loop -> "loopAnimation"
                                        BlockbenchModel.Animation.LoopType.Once -> "onceAnimation"
                                        BlockbenchModel.Animation.LoopType.Hold -> "holdAnimation"
                                    }

                                PropertySpec
                                    .builder(
                                        animation.name.toCamelCase(),
                                        modelAnimationClass,
                                    ).initializer(
                                        "$animationFunName(%L, %L)",
                                        (animation.length * 20).roundToLong(), // seconds -> ticks
                                        animation.animators
                                            .map { (uuid, animator) ->
                                                val part = partByUuid[uuid] ?: throw NotFoundPartException(uuid)
                                                part to animator
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
                                                                    BlockbenchModel.Animation.Animator.Keyframe.Channel.Rotation ->
                                                                        "rotation" to
                                                                            1.0
                                                                    BlockbenchModel.Animation.Animator.Keyframe.Channel.Position ->
                                                                        "position" to
                                                                            16.0
                                                                    BlockbenchModel.Animation.Animator.Keyframe.Channel.Scale ->
                                                                        "scale" to
                                                                            1.0
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
