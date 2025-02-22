package dev.s7a.animotion.convert.data.animotion

import dev.s7a.animotion.convert.data.blockbench.BlockbenchModel
import dev.s7a.animotion.convert.data.blockbench.Element
import dev.s7a.animotion.convert.data.blockbench.Element.Companion.toMinecraftElements
import dev.s7a.animotion.convert.data.blockbench.Outliner
import dev.s7a.animotion.convert.data.minecraft.model.MinecraftModel
import dev.s7a.animotion.convert.minecraft.MinecraftAsset
import dev.s7a.animotion.convert.model.Vector3
import dev.s7a.animotion.convert.util.createParentDirectory
import kotlinx.serialization.json.Json
import java.io.File
import java.util.concurrent.atomic.AtomicInteger
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Part(
    val customModelData: Int,
    val name: String,
    val uuid: Uuid,
    val origin: Vector3,
    val rotation: Vector3,
    val children: List<Part>,
    val model: MinecraftModel,
) {
    fun save(
        packDirectory: File,
        namespace: String,
        modelName: String,
        index: Int,
    ) {
        val file = MinecraftAsset.Model.resolve(packDirectory, "$modelName/$index", namespace)
        file.createParentDirectory()
        file.writeText(Json.encodeToString(model))
    }

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun from(
            model: BlockbenchModel,
            namespace: String,
            customModelData: AtomicInteger,
        ): List<Part> {
            val elementByUuid = model.elements.associateBy(Element::uuid)

            val textureSize = listOf(model.resolution.width, model.resolution.height)
            val textures = model.textures.indices.associate { "$it" to "$namespace:${model.name}/$it" }

            fun createParts(outliner: Outliner): List<Part> {
                val children = mutableListOf<Outliner.Child.UsePart>()
                val parts = mutableListOf<Part>()

                outliner.children.forEach { child ->
                    when (child) {
                        is Outliner.Child.UsePart -> {
                            children.add(child)
                        }
                        is Outliner.Child.UseOutliner -> {
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
                        outliner.name,
                        outliner.uuid,
                        outliner.origin,
                        outliner.rotation,
                        parts.toList(),
                        MinecraftModel(
                            textureSize,
                            textures,
                            children
                                .map(Outliner.Child.UsePart::uuid)
                                .mapNotNull(elementByUuid::get)
                                .toMinecraftElements(outliner, model.textures),
                        ),
                    ),
                )

                return parts
            }

            return model.outliner.flatMap(::createParts)
        }
    }
}
