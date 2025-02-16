package dev.s7a.animotion

import dev.s7a.animotion.common.BaseAnimation
import org.bukkit.entity.Player

class ModelAnimation(
    val parent: AnimotionModel,
    type: Type,
    length: Double,
    animators: Map<ModelPart, List<Pair<Double, Keyframe>>>,
) : BaseAnimation<ModelPart>(type, length, animators) {
    fun play(player: Player) = parent.play(player, this)

    fun isPlay(player: Player) = parent.isPlay(player, this)

    fun reset(player: Player) = parent.reset(player, this)
}
