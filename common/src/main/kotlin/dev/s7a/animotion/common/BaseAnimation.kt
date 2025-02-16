package dev.s7a.animotion.common

open class BaseAnimation<P : BasePart>(
    val type: Type,
    val length: Double,
    val animators: Map<P, List<Pair<Double, Keyframe>>>,
) {
    enum class Type {
        Loop,
        Once,
        Hold,
    }

    data class Keyframe(
        val channel: Channel,
        val value: Vector3,
    ) {
        enum class Channel {
            Position,
            Rotation,
            Scale,
        }
    }
}
