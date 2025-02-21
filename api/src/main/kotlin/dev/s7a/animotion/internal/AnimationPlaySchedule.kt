package dev.s7a.animotion.internal

import dev.s7a.animotion.ModelAnimation
import dev.s7a.animotion.ModelPart
import dev.s7a.animotion.common.BaseAnimation
import dev.s7a.animotion.common.Quaternion
import dev.s7a.animotion.common.Transformation
import dev.s7a.animotion.common.Vector3
import org.bukkit.entity.Player

internal class AnimationPlaySchedule(
    private val animation: ModelAnimation,
) {
    fun get(index: Int) = schedules.getOrNull(index)

    private val schedules =
        buildMap<Long, MutableList<(player: Player, next: (Int) -> Unit) -> Unit>> {
            // Animator
            val animators = animation.animators
            val transformations = mutableMapOf<Pair<ModelPart, Long>, Transformation>()
            animation.parent.orderedParts
                .forEach { part ->
                    val timeline = animators[part]
                    val positions =
                        if (timeline != null) {
                            interpolateChannel(timeline.positions)
                        } else {
                            emptyMap()
                        }
                    val scales =
                        if (timeline != null) {
                            interpolateChannel(timeline.scales)
                        } else {
                            emptyMap()
                        }
                    val rotations =
                        if (timeline != null) {
                            interpolateChannel(timeline.rotations).mapValues { it.value.toRadians().quaternion() }
                        } else {
                            emptyMap()
                        }
                    val lastPosition = positions.maxByOrNull(Map.Entry<Long, Vector3>::key)?.value
                    val lastScale = scales.maxByOrNull(Map.Entry<Long, Vector3>::key)?.value
                    val lastRotation = rotations.maxByOrNull(Map.Entry<Long, Quaternion>::key)?.value
                    (0..animation.length).forEach { ticks ->
                        val position = positions[ticks] ?: lastPosition
                        val scale = scales[ticks] ?: lastScale
                        val rotation = rotations[ticks] ?: lastRotation
                        val parent = transformations[part to ticks]
                        val transformation = Transformation.create(parent, part, position, scale, rotation)
                        part.children.forEach { child ->
                            transformations[child to ticks] = transformation
                        }
                        if (transformation.isNotNull) {
                            getOrPut(ticks, ::mutableListOf).add { player, _ ->
                                part.entity.transform(player, transformation)
                            }
                        }
                    }
                }

            // End action
            when (animation.type) {
                BaseAnimation.Type.Loop -> {
                    // Restart
                    getOrPut(animation.length, ::mutableListOf).add { _, next ->
                        next(0)
                    }
                }
                BaseAnimation.Type.Once -> {
                    getOrPut(animation.length, ::mutableListOf).add { player, _ ->
                        animation.parent.reset(player)
                    }
                }
                BaseAnimation.Type.Hold -> {
                    // Nothing
                }
            }
        }.toDelays()

    private fun <T> Map<Long, List<T>>.toDelays() =
        buildList {
            var previousTime = 0L
            toSortedMap().forEach { (time, actions) ->
                val delay = time - previousTime
                add(delay to actions.toList())
                previousTime = time
            }
        }
}
