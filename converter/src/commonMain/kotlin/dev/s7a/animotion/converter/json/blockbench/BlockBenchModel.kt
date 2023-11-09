package dev.s7a.animotion.converter.json.blockbench

import dev.s7a.animotion.converter.json.animotion.AnimotionSettings
import dev.s7a.animotion.converter.json.animotion.Part
import dev.s7a.animotion.converter.json.blockbench.Element.Companion.toMinecraftElements
import dev.s7a.animotion.converter.json.minecraft.model.MinecraftModel
import kotlinx.serialization.Serializable

@Serializable
data class BlockBenchModel(
    // TODO モデルをロードする前に Meta をチェックして必要に応じてローダーを変える
    val meta: Meta,
    val name: String,
    val resolution: Resolution,
    val elements: List<Element>,
    val outliner: List<Outliner>,
    val textures: List<Texture>,
    // TODO .anim に出力
    val animations: List<Animations>,
) {
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
