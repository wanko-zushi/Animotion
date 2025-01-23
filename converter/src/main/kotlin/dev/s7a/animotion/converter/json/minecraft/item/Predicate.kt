package dev.s7a.animotion.converter.json.minecraft.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Predicate(
    @SerialName("custom_model_data") val customModelData: Int,
)
