package dev.s7a.animotion.common

/**
 * Represents a base animation that consists of a sequence of keyframes associated with parts.
 *
 * @param P The type of parts being animated.
 * @param type The type of animation (e.g., loop, once, hold).
 * @param length The total length of the animation in seconds.
 * @param animators A map of parts to their associated list of keyframes, defined as pairs of time and keyframe data.
 */
open class BaseAnimation<P : BasePart>(
    val type: Type,
    val length: Double,
    val animators: Map<P, List<Pair<Double, Keyframe>>>,
) {
    /**
     * Specifies the type of the animation.
     */
    enum class Type {
        /**
         * The animation will loop indefinitely.
         */
        Loop,

        /**
         * The animation will play once and stop.
         */
        Once,

        /**
         * The animation will hold its final state upon completion.
         */
        Hold,
    }

    /**
     * Represents a single keyframe in an animation, defining a state for a specific channel.
     *
     * @param channel The channel being animated (e.g., position, rotation, scale).
     * @param value The value of the channel at this keyframe.
     */
    data class Keyframe(
        val channel: Channel,
        val value: Vector3,
    ) {
        /**
         * Specifies the channels that can be animated.
         */
        enum class Channel {
            /**
             * Channel affecting the position of the part.
             */
            Position,

            /**
             * Channel affecting the rotation of the part.
             */
            Rotation,

            /**
             * Channel affecting the scale of the part.
             */
            Scale,
        }
    }
}
