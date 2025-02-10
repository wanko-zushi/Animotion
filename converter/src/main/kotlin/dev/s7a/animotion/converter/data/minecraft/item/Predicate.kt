package dev.s7a.animotion.converter.data.minecraft.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Predicate(
    @SerialName("custom_model_data") val customModelData: Int,
)
