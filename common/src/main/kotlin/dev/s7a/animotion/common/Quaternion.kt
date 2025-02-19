package dev.s7a.animotion.common

/**
 * Represents a quaternion, a mathematical tool for handling 3D rotations or orientations.
 *
 * @property x The X component of the quaternion.
 * @property y The Y component of the quaternion.
 * @property z The Z component of the quaternion.
 * @property w The W component of the quaternion, representing the scalar part.
 */
data class Quaternion(
    val x: Double,
    val y: Double,
    val z: Double,
    val w: Double,
) {
    /**
     * Creates a default quaternion representing no rotation.
     */
    constructor() : this(0.0, 0.0, 0.0, 1.0)

    /**
     * Rotates a 3D vector using this quaternion.
     *
     * @param vector The 3D vector to rotate.
     * @return A new vector representing the result of the rotation.
     */
    fun rotate(vector: Vector3): Vector3 {
        val qVector = Quaternion(vector.x, vector.y, vector.z, 0.0)
        val rotated = this * qVector * this.conjugate()
        return Vector3(rotated.x, rotated.y, rotated.z)
    }

    /**
     * Calculates the conjugate of this quaternion.
     *
     * The conjugate negates the vector components (x, y, z) and retains the scalar (w).
     *
     * @return A new quaternion that is the conjugate of the current quaternion.
     */
    private fun conjugate() = Quaternion(-x, -y, -z, w)

    operator fun times(other: Quaternion): Quaternion =
        Quaternion(
            w * other.x + x * other.w + y * other.z - z * other.y,
            w * other.y - x * other.z + y * other.w + z * other.x,
            w * other.z + x * other.y - y * other.x + z * other.w,
            w * other.w - x * other.x - y * other.y - z * other.z,
        )
}
