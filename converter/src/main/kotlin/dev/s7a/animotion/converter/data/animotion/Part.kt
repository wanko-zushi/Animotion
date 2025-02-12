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

data class Part(
    val customModelData: Int,
    val outliner: Outliner,
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

            return model.outliner.map { outliner ->
                val textureSize = listOf(model.resolution.width, model.resolution.height)
                val textures = model.textures.indices.associate { "$it" to "$namespace:item/${model.name}/$it" }
                val elements =
                    outliner.children
                        .filterIsInstance<Outliner.Child.UsePart>() // FIXME Support UseOutliner
                        .map(Outliner.Child.UsePart::uuid)
                        .mapNotNull(
                            elementByUuid::get,
                        ).toMinecraftElements(outliner, model.resolution)
                Part(
                    customModelData.incrementAndGet(),
                    outliner,
                    MinecraftModel(textureSize, textures, elements),
                )
            }
        }
    }
}
