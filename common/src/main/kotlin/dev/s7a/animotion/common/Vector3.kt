package dev.s7a.animotion.common

data class Vector3(
    val x: Double,
    val y: Double,
    val z: Double,
) {
    constructor() : this(0.0, 0.0, 0.0)

    fun add(other: Vector3) = Vector3(x + other.x, y + other.y, z + other.z)

    fun multiply(other: Float) = Vector3(x * other, y * other, z * other)

    fun multiply(
        x: Int,
        y: Int,
        z: Int,
    ) = Vector3(this.x * x, this.y * y, this.z * z)

    val isZero
        get() = x == 0.0 && y == 0.0 && z == 0.0
}
