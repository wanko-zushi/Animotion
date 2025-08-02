package dev.s7a.animotion.convert.data

import kotlinx.serialization.Serializable

@Serializable
data class MinecraftItemModel(
    val model: Model,
) {
    @Serializable
    data class Model(
        val type: String,
        val model: String,
    )
}
