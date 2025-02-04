package dev.s7a.animotion

data class Rotation(
    val x: Double,
    val y: Double,
    val z: Double,
) {
    companion object {
        val Zero = Rotation(0.0, 0.0, 0.0)
    }
}
