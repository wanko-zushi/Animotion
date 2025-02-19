package dev.s7a.animotion.common

/**
 * Represents the base structure for an animation.
 *
 * @param P The type of parts used in the animation.
 * @property type The behavior type of the animation, such as loop or play once.
 * @property length The length of the animation in ticks.
 * @property animators A map associating parts with their corresponding timelines.
 */
open class BaseAnimation<P : BasePart>(
    val type: Type,
    val length: Long,
    val animators: Map<P, Timeline>,
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
     * Represents the timeline for a part of an animation, including transformations like position, rotation, and scale.
     *
     * @property positions A map of positions over time, with each entry containing a keyframe and interpolation method.
     * @property rotations A map of rotations over time, with each entry containing a keyframe and interpolation method.
     * @property scales A map of scales over time, with each entry containing a keyframe and interpolation method.
     */
    data class Timeline(
        val positions: Map<Long, Entry>,
        val rotations: Map<Long, Entry>,
        val scales: Map<Long, Entry>,
    ) {
        /**
         * Represents a single keyframe in the timeline, including its spatial value and interpolation method.
         *
         * @property keyframe A Vector3 representing the keyframe's position, rotation, or scale.
         * @property interpolation The interpolation type used to transition to the next keyframe.
         */
        data class Entry(
            val keyframe: Vector3,
            val interpolation: Interpolation,
        )

        /**
         * A helper class for constructing a `Timeline` with defined positions, rotations, and scales.
         */
        class Builder {
            private val positions =
                mutableMapOf(
                    0L to Entry(Transformation.DefaultPosition, Interpolation.Linear),
                )
            private val rotations =
                mutableMapOf(
                    0L to Entry(Transformation.DefaultRotation, Interpolation.Linear),
                )
            private val scales =
                mutableMapOf(
                    0L to Entry(Transformation.DefaultScale, Interpolation.Linear),
                )

            /**
             * Adds a position keyframe to the timeline.
             *
             * @param ticks The time in ticks for this keyframe.
             * @param x The x-coordinate for the keyframe.
             * @param y The y-coordinate for the keyframe.
             * @param z The z-coordinate for the keyframe.
             * @param interpolation The interpolation type used to transition to this keyframe.
             */
            fun position(
                ticks: Long,
                x: Double,
                y: Double,
                z: Double,
                interpolation: Interpolation = Interpolation.Linear,
            ) {
                positions[ticks] = Entry(Vector3(x, y, z), interpolation)
            }

            /**
             * Adds a rotation keyframe to the timeline.
             *
             * @param ticks The time in ticks for this keyframe.
             * @param x The rotation around the x-axis in degrees.
             * @param y The rotation around the y-axis in degrees.
             * @param z The rotation around the z-axis in degrees.
             * @param interpolation The interpolation type used to transition to this keyframe.
             */
            fun rotation(
                ticks: Long,
                x: Double,
                y: Double,
                z: Double,
                interpolation: Interpolation = Interpolation.Linear,
            ) {
                rotations[ticks] = Entry(Vector3(x, y, z), interpolation)
            }

            /**
             * Adds a scale keyframe to the timeline.
             *
             * @param ticks The time in ticks for this keyframe.
             * @param x The scale factor along the x-axis.
             * @param y The scale factor along the y-axis.
             * @param z The scale factor along the z-axis.
             * @param interpolation The interpolation type used to transition to this keyframe.
             */
            fun scale(
                ticks: Long,
                x: Double,
                y: Double,
                z: Double,
                interpolation: Interpolation = Interpolation.Linear,
            ) {
                scales[ticks] = Entry(Vector3(x, y, z), interpolation)
            }

            fun build() = Timeline(positions.toMap(), rotations.toMap(), scales.toMap())
        }
    }

    /**
     * Describes the interpolation method for transitioning between animation keyframes.
     */
    enum class Interpolation {
        /**
         * Linear interpolation provides a smooth and constant rate of change.
         */
        Linear,

        /**
         * Catmull-Rom spline interpolation provides smoother transitions between keyframes,
         * reducing sharp changes in animation by taking into account neighboring keyframes.
         */
        Catmullrom,
    }
}
