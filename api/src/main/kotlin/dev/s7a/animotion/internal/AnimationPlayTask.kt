package dev.s7a.animotion.internal

import dev.s7a.animotion.ModelAnimation
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask

internal class AnimationPlayTask(
    private val player: Player,
    val animation: ModelAnimation,
) {
    private lateinit var currentTask: BukkitTask

    init {
        next(0)
    }

    fun cancel() {
        currentTask.cancel()
    }

    private fun next(cursor: Int) {
        val (delay, actions) = animation.schedule.get(cursor) ?: return
        currentTask =
            animation.parent.animotion.runTaskLaterAsync(delay) {
                actions.forEach { it(player, ::next) }
                next(cursor + 1)
            }
    }
}
