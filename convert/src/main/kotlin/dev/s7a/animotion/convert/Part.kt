package dev.s7a.animotion.convert

import dev.s7a.animotion.convert.data.BlockbenchModel
import dev.s7a.animotion.convert.data.MinecraftModel
import dev.s7a.animotion.convert.data.Vector3
import dev.s7a.animotion.convert.minecraft.getElementRotation
import java.util.concurrent.atomic.AtomicInteger
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Part(
    val customModelData: Int,
    val model: BlockbenchModel,
    val name: String,
    val uuid: Uuid,
    val origin: Vector3,
    val rotation: Vector3,
    val parts: List<Part>,
    val children: List<Uuid>,
) {
    fun toMinecraftModel(namespace: String): MinecraftModel {
        val elementByUuid = model.elements.associateBy(BlockbenchModel.Element::uuid)
        val textureSize = listOf(model.resolution.width, model.resolution.height)
        val textures = model.textures.indices.associate { "$it" to "$namespace:${model.name}/$it" }
        return MinecraftModel(
            textureSize,
            textures,
            children
                .mapNotNull(elementByUuid::get)
                .map { element ->
                    val (angle, axis) = getElementRotation(element.rotation - rotation)
                    val center = Vector3(8.0)
                    MinecraftModel.Element(
                        element.from + center - origin,
                        element.to + center - origin,
                        MinecraftModel.Element.Rotation(
                            angle,
                            axis,
                            element.origin + center - origin,
                        ),
                        element.faces.mapValues { (_, face) ->
                            val textureIndex = face.texture
                            if (textureIndex == null) {
                                MinecraftModel.Element.Face.Missing
                            } else {
                                val texture = model.textures[textureIndex]
                                val uvWidth = texture.uvWidth
                                val uvHeight = texture.uvHeight
                                MinecraftModel.Element.Face(
                                    face.uv.mapIndexed { index, value ->
                                        val uvSize = if (index % 2 == 0) uvWidth else uvHeight
                                        value * 16 / uvSize
                                    },
                                    "#$textureIndex",
                                )
                            }
                        },
                    )
                },
        )
    }

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun from(
            model: BlockbenchModel,
            customModelData: AtomicInteger,
        ): List<Part> {
            fun createParts(outliner: BlockbenchModel.Outliner): List<Part> {
                val children = mutableListOf<BlockbenchModel.Outliner.Child.UsePart>()
                val parts = mutableListOf<Part>()

                outliner.children.forEach { child ->
                    when (child) {
                        is BlockbenchModel.Outliner.Child.UsePart -> {
                            children.add(child)
                        }
                        is BlockbenchModel.Outliner.Child.UseOutliner -> {
                            parts.addAll(
                                createParts(
                                    child.outliner,
                                ),
                            )
                        }
                    }
                }

                parts.add(
                    Part(
                        customModelData.incrementAndGet(),
                        model,
                        outliner.name,
                        outliner.uuid,
                        outliner.origin,
                        outliner.rotation,
                        parts.toList(),
                        children.map(BlockbenchModel.Outliner.Child.UsePart::uuid),
                    ),
                )

                return parts
            }

            return model.outliner.flatMap(::createParts)
        }
    }
}
