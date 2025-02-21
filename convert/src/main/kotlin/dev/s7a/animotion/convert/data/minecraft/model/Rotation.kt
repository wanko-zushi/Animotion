package dev.s7a.animotion.convert.data.minecraft.model

import dev.s7a.animotion.convert.model.Vector3
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rotation(
    val angle: Double,
    val axis: Axis,
    val origin: Vector3,
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
