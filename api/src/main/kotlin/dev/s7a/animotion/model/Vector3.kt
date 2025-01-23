package dev.s7a.animotion.model

import kotlinx.serialization.Serializable

@Serializable
public data class Vector3(
    val x: Double,
    val y: Double,
    val z: Double,
) {
    public fun divide(value: Double): Vector3 = Vector3(x / value, y / value, z / value)

    public companion object {
        public val Zero: Vector3 = Vector3(0.0, 0.0, 0.0)
    }
}
