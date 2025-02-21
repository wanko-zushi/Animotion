package dev.s7a.animotion.convert.model

import dev.s7a.animotion.convert.serializers.Vector3Serializer
import kotlinx.serialization.Serializable

@Serializable(with = Vector3Serializer::class)
data class Vector3(
    val x: Double,
    val y: Double,
    val z: Double,
) {
    constructor(value: Double) : this(value, value, value)

    constructor() : this(0.0, 0.0, 0.0)

    operator fun plus(other: Vector3) = Vector3(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Vector3) = Vector3(x - other.x, y - other.y, z - other.z)

    operator fun times(value: Double) = Vector3(x * value, y * value, z * value)

    operator fun div(value: Double) = Vector3(x / value, y / value, z / value)

    fun toList() = listOf(x, y, z)

    fun toTypedArray() = arrayOf(x, y, z)

    val isZero
        get() = x == 0.0 && y == 0.0 && z == 0.0
}
