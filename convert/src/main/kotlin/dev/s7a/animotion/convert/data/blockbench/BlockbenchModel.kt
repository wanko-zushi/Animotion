package dev.s7a.animotion.convert.data.blockbench

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class BlockbenchModel(
    // TODO モデルをロードする前に Meta をチェックして必要に応じてローダーを変える
    val meta: Meta,
    val name: String,
    val resolution: Resolution,
    val elements: List<Element>,
    val outliner: List<Outliner>,
    val textures: List<Texture>,
    val animations: List<Animation>,
) {
    companion object {
        private val json =
            Json {
                ignoreUnknownKeys = true
            }

        fun load(file: File) = json.decodeFromString<BlockbenchModel>(file.readText())
    }
}
