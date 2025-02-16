package dev.s7a.animotion.convert.data.blockbench

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
    val animations: List<Animation>,
)
