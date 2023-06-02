package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    @SerialName("format_version") val formatVersion: String, // TODO 4.5 であることをチェックする
    @SerialName("model_format") val modelFormat: ModelFormat,
) {
    @Serializable
    enum class ModelFormat {
        @SerialName("free")
        Free,
    }
}
