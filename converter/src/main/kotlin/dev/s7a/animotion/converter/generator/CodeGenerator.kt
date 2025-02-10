package dev.s7a.animotion.converter.generator

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
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
        val partClass = ClassName("dev.s7a.animotion.data", "Part")
        val materialClass = ClassName("org.bukkit", "Material")
        val vectorClass = ClassName("org.bukkit.util", "Vector")
        val animationClass = ClassName("dev.s7a.animotion.data", "Animation")

        resourcePack.animotion.models.forEach { (model, parts) ->
            val className = model.name.toPascalCase()
            val partByUuid = mutableMapOf<Uuid, Pair<Part, String>>()

            FileSpec
                .builder(resourcePack.animotion.settings.`package`, className)
                .addAnnotation(AnnotationSpec.builder(Suppress::class).addMember("%S", "ktlint").build())
                .addType(
                    TypeSpec
                        .classBuilder(className)
                        .primaryConstructor(
                            FunSpec
                                .constructorBuilder()
                                .addParameter("animotion", animotionClass)
                                .build(),
                        ).superclass(animotionModelClass)
                        .addSuperclassConstructorParameter("%N", "animotion")
                        .addProperties(
                            parts.mapIndexed { index, part ->
                                val partName = part.outliner.name.toCamelCase()
                                partByUuid[part.outliner.uuid] = part to partName

                                PropertySpec
                                    .builder(
                                        partName,
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

                                            // position
                                            add(CodeBlock.of("%T(%L, %L, %L)", vectorClass, *part.outliner.origin.toTypedArray()))

                                            // rotation
                                            if (part.outliner.rotation != null) {
                                                add(CodeBlock.of("%T(%L, %L, %L)", vectorClass, *part.outliner.rotation.toTypedArray()))
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
                                            .map { (uuid, animator) ->
                                                val (part, partName) = partByUuid[uuid] ?: throw NotFoundPartException()
                                                Triple(part, partName, animator)
                                            }.sortedBy { (part) ->
                                                part.customModelData
                                            }.map { (_, partName, animator) ->
                                                CodeBlock.of(
                                                    "%N to listOf(%L)",
                                                    partName,
                                                    animator.keyframes
                                                        .sortedBy { it.time }
                                                        .map { keyframe ->
                                                            val keyframeFunName =
                                                                when (keyframe.channel) {
                                                                    Keyframes.Channel.Rotation -> "rotation"
                                                                    Keyframes.Channel.Position -> "position"
                                                                    Keyframes.Channel.Scale -> "scale"
                                                                }

                                                            val point = keyframe.dataPoints[0]
                                                            CodeBlock.of(
                                                                "%L to %N(%L, %L, %L)",
                                                                keyframe.time,
                                                                keyframeFunName,
                                                                point.x,
                                                                point.y,
                                                                point.z,
                                                            )
                                                        }.joinToCode(),
                                                )
                                            }.joinToCode(),
                                    ).build()
                            },
                        ).build(),
                ).build()
                .writeTo(parent)
        }
    }
}
