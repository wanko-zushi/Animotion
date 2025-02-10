package dev.s7a.animotion.internal

import dev.s7a.animotion.data.Animation
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import kotlin.math.roundToLong

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
                    keyframes.forEach { (time, keyframe) ->
                        add(time.toTicks() to { entity.transform(player, part, keyframe) })
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
            animation.model.animotion.runTaskLaterAsync(delay) {
                actions.forEach { it() }
                next(cursor + 1)
            }
    }

    private fun Double.toTicks() = (this * 20).roundToLong()

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
