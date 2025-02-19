package dev.s7a.animotion.common

data class Quaternion(
    val x: Double,
    val y: Double,
    val z: Double,
    val w: Double,
) {
    constructor() : this(0.0, 0.0, 0.0, 1.0)

    fun rotate(vector: Vector3): Vector3 {
        val qVector = Quaternion(vector.x, vector.y, vector.z, 0.0)
        val rotated = this * qVector * this.conjugate()
        return Vector3(-rotated.x, -rotated.y, -rotated.z)
    }

    private fun conjugate() = Quaternion(-x, -y, -z, w)

    operator fun times(other: Quaternion): Quaternion =
        Quaternion(
            w * other.x + x * other.w + y * other.z - z * other.y,
            w * other.y - x * other.z + y * other.w + z * other.x,
            w * other.z + x * other.y - y * other.x + z * other.w,
            w * other.w - x * other.x - y * other.y - z * other.z,
        )
}
