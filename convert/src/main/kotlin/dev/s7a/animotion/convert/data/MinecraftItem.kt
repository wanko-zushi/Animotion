package dev.s7a.animotion.convert.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinecraftItem(
    val parent: String,
    val textures: Map<String, String>,
    val overrides: List<Override>,
) {
    @Serializable
    data class Override(
        val predicate: Predicate,
        val model: String,
    ) {
        @Serializable
        data class Predicate(
            @SerialName("custom_model_data") val customModelData: Int,
        )
    }
}
