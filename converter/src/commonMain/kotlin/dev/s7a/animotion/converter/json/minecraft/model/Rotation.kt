package dev.s7a.animotion.converter.json.minecraft.model

import kotlinx.serialization.Serializable

@Serializable
data class Rotation(
    val angle: Double,
    val axis: String,
    val origin: List<Double>,
)
