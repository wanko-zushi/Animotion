package dev.s7a.animotion.common

import kotlin.math.cos
import kotlin.math.sin

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

    operator fun plus(other: Vector3) = add(other)

    operator fun minus(other: Vector3) = add(-other)

    operator fun times(scalar: Double) = multiply(scalar)

    operator fun times(other: Vector3) = multiply(other)

    operator fun unaryMinus() = Vector3(-x, -y, -z)

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
     * @param scalar The scalar value to multiply by.
     * @return A new vector resulting from the multiplication.
     */
    fun multiply(scalar: Float) = Vector3(x * scalar, y * scalar, z * scalar)

    /**
     * Multiplies this vector by a scalar value.
     *
     * @param scalar The scalar value to multiply by.
     * @return A new vector resulting from the multiplication.
     */
    fun multiply(scalar: Double) = Vector3(x * scalar, y * scalar, z * scalar)

    /**
     * Multiplies this vector by another vector, component-wise.
     *
     * @param other The vector to multiply with.
     * @return A new vector resulting from the component-wise multiplication.
     */
    fun multiply(other: Vector3) = Vector3(x * other.x, y * other.y, z * other.z)

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

    /**
     * Converts the components of this vector from degrees to radians.
     *
     * @return A new vector with its components in radians.
     */
    fun toRadians() = Vector3(Math.toRadians(x), Math.toRadians(y), Math.toRadians(z))

    /**
     * Converts this vector into a quaternion representation.
     * The resulting quaternion represents a rotation in 3D space.
     *
     * @return A quaternion calculated from the vector's components.
     */
    fun quaternion(): Quaternion {
        val cx = cos(x / 2)
        val cy = cos(-y / 2)
        val cz = cos(-z / 2)
        val sx = sin(x / 2)
        val sy = sin(-y / 2)
        val sz = sin(-z / 2)

        return Quaternion(
            sx * cy * cz - cx * sy * sz,
            cx * sy * cz + sx * cy * sz,
            cx * cy * sz - sx * sy * cz,
            cx * cy * cz + sx * sy * sz,
        )
    }
}
