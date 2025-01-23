package dev.s7a.animotion.model

import kotlinx.serialization.Serializable

@Serializable
public data class Vector3(
    val x: Double,
    val y: Double,
    val z: Double,
) {
    public companion object {
        public val Zero: Vector3 = Vector3(0.0, 0.0, 0.0)
    }
}
