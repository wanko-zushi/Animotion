package dev.s7a.animotion.model

import dev.s7a.animotion.AnimotionModel
import org.bukkit.entity.Player

data class Animation(
    val parent: AnimotionModel,
    val type: Type,
    val length: Double,
    val animators: Map<Part, List<Pair<Double, Keyframe>>>,
) {
    enum class Type {
        Loop,
        Once,
        Hold,
    }

    fun play(player: Player) = parent.play(player, this)

    fun isPlay(player: Player) = parent.isPlay(player, this)

    fun reset(player: Player) = parent.reset(player, this)
}
