package dev.s7a.animotion.data

import org.bukkit.util.Vector

data class Keyframe(
    val channel: Channel,
    val value: Vector,
) {
    enum class Channel {
        Position,
        Rotation,
        Scale,
    }
}
