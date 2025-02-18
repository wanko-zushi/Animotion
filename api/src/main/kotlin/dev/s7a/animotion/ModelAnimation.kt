package dev.s7a.animotion

import dev.s7a.animotion.common.BaseAnimation
import org.bukkit.entity.Player

/**
 * Represents an animation for a model in the animation framework.
 * This class allows interaction with a specific model animation by providing methods
 * to play, check the state, and reset the animation for a player.
 *
 * @param parent The parent `AnimotionModel` that manages this animation.
 * @param type The type of the animation.
 * @param length The duration of the animation in ticks.
 * @param animators A map of model parts to their associated keyframe animations.
 */
class ModelAnimation(
    val parent: AnimotionModel,
    type: Type,
    length: Long,
    animators: Map<ModelPart, Timeline>,
) : BaseAnimation<ModelPart>(type, length, animators) {
    /**
     * Plays this animation for the specified player.
     *
     * @param player The player for whom the animation will be played.
     */
    fun play(player: Player) = parent.play(player, this)

    /**
     * Checks if this animation is currently being played for the specified player.
     *
     * @param player The player whose animation state is to be checked.
     * @return `true` if the animation is being played for the player, `false` otherwise.
     */
    fun isPlay(player: Player) = parent.isPlay(player, this)

    /**
     * Resets this animation for the specified player, restoring the model's state to its original position.
     *
     * @param player The player for whom the animation will be reset.
     */
    fun reset(player: Player) = parent.reset(player, this)
}
