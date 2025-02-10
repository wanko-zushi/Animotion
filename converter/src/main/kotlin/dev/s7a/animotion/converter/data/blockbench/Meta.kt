package dev.s7a.animotion.converter.data.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    // TODO 4.5 であることをチェックする
    @SerialName("format_version") val formatVersion: String,
    @SerialName("model_format") val modelFormat: ModelFormat,
) {
    @Serializable
    enum class ModelFormat {
        @SerialName("free")
        Free,
    }
}
