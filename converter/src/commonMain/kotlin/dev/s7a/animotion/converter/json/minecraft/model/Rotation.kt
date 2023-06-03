package dev.s7a.animotion.converter.json.minecraft.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rotation(
    val angle: Double,
    val axis: Axis,
    val origin: List<Double>,
) {
    @Serializable
    enum class Axis {
        @SerialName("x")
        X,

        @SerialName("y")
        Y,

        @SerialName("z")
        Z,
    }
}
