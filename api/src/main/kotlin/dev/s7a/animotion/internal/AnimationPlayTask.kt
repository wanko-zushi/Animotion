package dev.s7a.animotion.internal

import dev.s7a.animotion.data.Animation
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import kotlin.math.roundToInt

internal class AnimationPlayTask(
    private val player: Player,
    val animation: Animation,
) {
    private val schedules =
        buildList {
            // Animator
            animation.animators
                .forEach { (part, keyframes) ->
                    val entity = animation.model.get(part)
                    var previousTicks = 0
                    keyframes.forEach { (time, keyframe) ->
                        val ticks = time.toTicks()
                        val duration = ticks - previousTicks
                        previousTicks = ticks
                        add(ticks to { entity.transform(player, part, keyframe, duration) })
                    }
                }

            // End action
            when (animation.type) {
                Animation.Type.Loop -> {
                    // Restart
                    add(animation.length.toTicks() to { next(0) })
                }
                Animation.Type.Once -> {
                    add(animation.length.toTicks() to { animation.model.reset(player) })
                }
                Animation.Type.Hold -> {
                    // Nothing
                }
            }
        }.groupBy({ it.first }, { it.second })
            .toDelays()
            .toMutableList()

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
            animation.model.animotion.runTaskLaterAsync(delay.toLong()) {
                actions.forEach { it() }
                next(cursor + 1)
            }
    }

    private fun Double.toTicks() = (this * 20).roundToInt()

    private fun <T> Map<Int, T>.toDelays() =
        buildList<Pair<Int, T>> {
            var previousTime = 0
            toSortedMap().forEach { (time, actions) ->
                val delay = time - previousTime
                add(delay to actions)
                previousTime = time
            }
        }
}
