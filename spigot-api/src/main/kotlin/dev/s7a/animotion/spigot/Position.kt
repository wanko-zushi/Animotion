package dev.s7a.animotion.spigot

data class Position(
    val x: Double,
    val y: Double,
    val z: Double,
) {
    companion object {
        val Zero = Position(0.0, 0.0, 0.0)
    }
}
