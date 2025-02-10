package dev.s7a.animotion.data

import dev.s7a.animotion.AnimotionModel
import org.bukkit.entity.Player

data class Animation(
    val model: AnimotionModel,
    val type: Type,
    val length: Double,
    val animators: Map<Part, List<Pair<Double, Keyframe>>>,
) {
    enum class Type {
        Loop,
        Once,
        Hold,
    }

    fun play(player: Player) = model.play(player, this)

    fun isPlay(player: Player) = model.isPlay(player, this)

    fun reset(player: Player) = model.reset(player, this)
}
