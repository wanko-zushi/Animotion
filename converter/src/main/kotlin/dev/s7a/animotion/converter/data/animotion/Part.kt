package dev.s7a.animotion.converter.data.animotion

import dev.s7a.animotion.converter.data.blockbench.BlockBenchModel
import dev.s7a.animotion.converter.data.blockbench.Element
import dev.s7a.animotion.converter.data.blockbench.Element.Companion.toMinecraftElements
import dev.s7a.animotion.converter.data.minecraft.model.MinecraftModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.uuid.ExperimentalUuidApi

@Serializable
data class Part(
    val name: String,
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
        ): List<Part> {
            val elementByUuid = model.elements.associateBy(Element::uuid)

            return model.outliner.map { outliner ->
                val textureSize = listOf(model.resolution.width, model.resolution.height)
                val textures = model.textures.indices.associate { "$it" to "$namespace:item/${model.name}/$it" }
                val elements = outliner.children.mapNotNull(elementByUuid::get).toMinecraftElements(outliner, model.resolution)
                Part(outliner.name, MinecraftModel(textureSize, textures, elements))
            }
        }
    }
}
