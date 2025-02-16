package dev.s7a.animotion.common

/**
 * A class representing a 3D vector.
 *
 * @property x The x-coordinate of the vector.
 * @property y The y-coordinate of the vector.
 * @property z The z-coordinate of the vector.
 */
data class Vector3(
    val x: Double,
    val y: Double,
    val z: Double,
) {
    /**
     * Creates a vector with all components initialized to zero.
     */
    constructor() : this(0.0, 0.0, 0.0)

    /**
     * Adds another vector to this vector.
     *
     * @param other The other vector to add.
     * @return A new vector resulting from the addition.
     */
    fun add(other: Vector3) = Vector3(x + other.x, y + other.y, z + other.z)

    /**
     * Multiplies this vector by a scalar value.
     *
     * @param other The scalar value to multiply by.
     * @return A new vector resulting from the multiplication.
     */
    fun multiply(other: Float) = Vector3(x * other, y * other, z * other)

    /**
     * Multiplies this vector by independent scaling factors for each axis.
     *
     * @param x The scaling factor for the x-axis.
     * @param y The scaling factor for the y-axis.
     * @param z The scaling factor for the z-axis.
     * @return A new vector resulting from the scaling.
     */
    fun multiply(
        x: Int,
        y: Int,
        z: Int,
    ) = Vector3(this.x * x, this.y * y, this.z * z)

    /**
     * Checks if this vector is a zero vector (all components are zero).
     *
     * @return `true` if this vector is a zero vector, otherwise `false`.
     */
    val isZero
        get() = x == 0.0 && y == 0.0 && z == 0.0
}
