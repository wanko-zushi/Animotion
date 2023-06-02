package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.Serializable

@Serializable
data class BlockBenchModel(
    val meta: Meta, // TODO モデルをロードする前に Meta をチェックして必要に応じてローダーを変える
    val resolution: Resolution, // TODO MinecraftModel: texture_size
    val elements: List<Element>,
    val outliner: List<Outliner>, // TODO このグループ単位で MinecraftModel を生成
    val textures: List<Textures>, // TODO このデータを assets/animotion/textures に出力
    val animations: List<Animations>,
)
