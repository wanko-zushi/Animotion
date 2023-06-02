package dev.s7a.animotion.converter.json.blockbench

import dev.s7a.animotion.converter.json.animotion.AnimotionSettings
import dev.s7a.animotion.converter.json.blockbench.Element.Companion.toMinecraftElements
import dev.s7a.animotion.converter.json.minecraft.MinecraftModel
import kotlinx.serialization.Serializable

@Serializable
data class BlockBenchModel(
    val meta: Meta, // TODO モデルをロードする前に Meta をチェックして必要に応じてローダーを変える
    val name: String,
    val resolution: Resolution,
    val elements: List<Element>,
    val outliner: List<Outliner>,
    val textures: List<Texture>, // TODO このデータを assets/animotion/textures に出力
    val animations: List<Animations>,
) {
    fun toMinecraftModels(settings: AnimotionSettings): List<MinecraftModel> {
        val elements = elements.associateBy(Element::uuid)

        return outliner.map { outliner ->
            MinecraftModel(
                textureSize = listOf(resolution.width, resolution.height),
                textures = textures.indices.associate { "$it" to "${settings.namespace}:$name/$it" },
                elements = outliner.children.mapNotNull(elements::get).toMinecraftElements(),
            )
        }
    }
}
