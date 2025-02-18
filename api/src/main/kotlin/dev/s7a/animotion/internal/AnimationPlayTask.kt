package dev.s7a.animotion.internal

import dev.s7a.animotion.ModelAnimation
import dev.s7a.animotion.common.BaseAnimation
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask

internal class AnimationPlayTask(
    private val player: Player,
    val animation: ModelAnimation,
) {
    private val schedules =
        buildMap<Long, MutableList<() -> Unit>> {
            // Animator
            animation.animators
                .forEach { (part, timeline) ->
                    val positions = interpolateChannel(timeline.positions)
                    val scales = interpolateChannel(timeline.scales)
                    val rotations = interpolateChannel(timeline.rotations)
                    (0..animation.length).forEach { ticks ->
                        val position = positions[ticks]
                        val scale = scales[ticks]
                        val rotation = rotations[ticks]
                        if (position != null || scale != null || rotation != null) {
                            getOrPut(ticks, ::mutableListOf).add {
                                part.entity.transform(player, position, scale, rotation)
                            }
                        }
                    }
                }

            // End action
            when (animation.type) {
                BaseAnimation.Type.Loop -> {
                    // Restart
                    getOrPut(animation.length, ::mutableListOf).add {
                        next(0)
                    }
                }
                BaseAnimation.Type.Once -> {
                    getOrPut(animation.length, ::mutableListOf).add {
                        animation.parent.reset(player)
                    }
                }
                BaseAnimation.Type.Hold -> {
                    // Nothing
                }
            }
        }.toDelays()

    private lateinit var currentTask: BukkitTask

    init {
        next(0)
    }

    fun cancel() {
        currentTask.cancel()
    }

    private fun next(cursor: Int) {
        val (delay, actions) = schedules.getOrNull(cursor) ?: return
        currentTask =
            animation.parent.animotion.runTaskLaterAsync(delay) {
                actions.forEach { it() }
                next(cursor + 1)
            }
    }

    private fun <T> Map<Long, T>.toDelays() =
        buildList<Pair<Long, T>> {
            var previousTime = 0L
            toSortedMap().forEach { (time, actions) ->
                val delay = time - previousTime
                add(delay to actions)
                previousTime = time
            }
        }
}
