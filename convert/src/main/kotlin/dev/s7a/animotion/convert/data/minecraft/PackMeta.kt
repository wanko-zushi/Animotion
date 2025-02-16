package dev.s7a.animotion.convert.data.minecraft

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PackMeta(
    val pack: Pack,
) {
    @Serializable
    data class Pack(
        @SerialName("pack_format") val packFormat: Int,
    )
}
