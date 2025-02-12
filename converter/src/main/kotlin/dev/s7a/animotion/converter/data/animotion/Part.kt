package dev.s7a.animotion.converter.data.animotion

import dev.s7a.animotion.converter.data.blockbench.BlockBenchModel
import dev.s7a.animotion.converter.data.blockbench.Element
import dev.s7a.animotion.converter.data.blockbench.Element.Companion.toMinecraftElements
import dev.s7a.animotion.converter.data.blockbench.Outliner
import dev.s7a.animotion.converter.data.minecraft.model.MinecraftModel
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
    val origin: List<Double>,
    val rotation: List<Double>,
    val model: MinecraftModel,
) {
    fun save(
        destination: File,
        namespace: String,
        modelName: String,
        index: Int,
    ) {
        destination.resolve("assets/$namespace/models/$modelName/$index.json").run {
            parentFile?.mkdirs()
            writeText(Json.encodeToString(model))
        }
    }

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun from(
            model: BlockBenchModel,
            namespace: String,
            customModelData: AtomicInteger,
        ): List<Part> {
            val elementByUuid = model.elements.associateBy(Element::uuid)

            val textureSize = listOf(model.resolution.width, model.resolution.height)
            val textures = model.textures.indices.associate { "$it" to "$namespace:item/${model.name}/$it" }

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
                        MinecraftModel(
                            textureSize,
                            textures,
                            children
                                .map(Outliner.Child.UsePart::uuid)
                                .mapNotNull(elementByUuid::get)
                                .toMinecraftElements(outliner, model.resolution),
                        ),
                    ),
                )

                return parts
            }

            return model.outliner.flatMap(::createParts)
        }
    }
}
