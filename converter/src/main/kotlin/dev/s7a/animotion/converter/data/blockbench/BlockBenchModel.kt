package dev.s7a.animotion.converter.data.blockbench

import dev.s7a.animotion.converter.data.animotion.AnimotionSettings
import dev.s7a.animotion.converter.data.animotion.Part
import dev.s7a.animotion.converter.data.blockbench.Element.Companion.toMinecraftElements
import dev.s7a.animotion.converter.data.minecraft.model.MinecraftModel
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi

@Serializable
data class BlockBenchModel(
    // TODO モデルをロードする前に Meta をチェックして必要に応じてローダーを変える
    val meta: Meta,
    val name: String,
    val resolution: Resolution,
    val elements: List<Element>,
    val outliner: List<Outliner>,
    val textures: List<Texture>,
    val animations: List<Animation>,
) {
    @OptIn(ExperimentalUuidApi::class)
    fun toParts(settings: AnimotionSettings): List<Part> {
        val elementByUuid = elements.associateBy(Element::uuid)

        return outliner.map { outliner ->
            val textureSize = listOf(resolution.width, resolution.height)
            val textures = textures.indices.associate { "$it" to "${settings.namespace}:item/$name/$it" }
            val elements = outliner.children.mapNotNull(elementByUuid::get).toMinecraftElements(outliner, resolution)
            Part(outliner.name, MinecraftModel(textureSize, textures, elements))
        }
    }
}
