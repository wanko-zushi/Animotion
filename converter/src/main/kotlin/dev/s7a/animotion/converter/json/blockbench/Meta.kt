package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    @SerialName("format_version") var formatVersion: String? = null,
    @SerialName("model_format") var modelFormat: String? = null,
    @SerialName("box_uv") var boxUv: Boolean? = null,
)
