package dev.s7a.animotion.converter.json.blockbench

import dev.s7a.animotion.converter.json.animotion.AnimotionSettings
import dev.s7a.animotion.converter.json.animotion.Part
import dev.s7a.animotion.converter.json.blockbench.Element.Companion.toMinecraftElements
import dev.s7a.animotion.converter.json.minecraft.model.MinecraftModel
import kotlinx.serialization.Serializable

@Serializable
data class BlockBenchModel(
    val meta: Meta, // TODO モデルをロードする前に Meta をチェックして必要に応じてローダーを変える
    val name: String,
    val resolution: Resolution,
    val elements: List<Element>,
    val outliner: List<Outliner>,
    val textures: List<Texture>,
    val animations: List<Animations>, // TODO .anim に出力
) {
    fun toParts(settings: AnimotionSettings): List<Part> {
        val elementByUuid = elements.associateBy(Element::uuid)

        return outliner.map { outliner ->
            val textureSize = listOf(resolution.width, resolution.height)
            val textures = textures.indices.associate { "$it" to "${settings.namespace}:item/$name/$it" }
            val elements = outliner.children.mapNotNull(elementByUuid::get).toMinecraftElements(resolution)
            Part(outliner.name, MinecraftModel(textureSize, textures, elements))
        }
    }
}
