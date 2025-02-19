package dev.s7a.animotion

import dev.s7a.animotion.common.BaseAnimation
import dev.s7a.animotion.common.Vector3
import dev.s7a.animotion.internal.AnimationPlayTask
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.UUID

/**
 * **This class is used by auto-generated model classes.**
 *
 * Represents an animation-based model consisting of multiple transformable parts.
 *
 * This class provides functionality for creating, managing, and animating custom model parts
 * within the game. It supports multiple animation types (loop, hold, and once) and keyframe-based
 * transformations for position, rotation, and scale.
 *
 * @param animotion The Animotion instance managing this model.
 * @param baseScale The base scale of the model.
 */
abstract class AnimotionModel(
    internal val animotion: Animotion,
    val baseScale: Float,
) {
    private val parts = mutableListOf<ModelPart>()
    private val playTasks = mutableMapOf<UUID, AnimationPlayTask>()

    val orderedParts: List<ModelPart>
        get() {
            val sortedParts = mutableListOf<ModelPart>()
            val remain = parts.toMutableList()
            while (remain.isNotEmpty()) {
                remain.toList().forEach { part ->
                    if (part.children.all(sortedParts::contains)) {
                        sortedParts.add(part)
                        remain.remove(part)
                    }
                }
            }
            return sortedParts.reversed()
        }

    /**
     * Creates a new model part and adds it to the list of parts.
     *
     * @param itemModel The string identifier for the item model.
     * @param customModelData The custom model data value to assign to this part.
     * @param position The initial position of the part, defaulting to (0, 0, 0).
     * @param rotation The initial rotation of the part, defaulting to (0, 0, 0).
     * @return The newly created and added model part.
     */
    protected fun part(
        itemModel: String,
        customModelData: Int,
        position: Vector3 = Vector3(),
        rotation: Vector3 = Vector3(),
    ) = ModelPart(this, itemModel, customModelData, position, rotation).apply(parts::add)

    /**
     * Creates a looping animation for the specified model parts.
     *
     * @param length The duration of the animation in ticks.
     * @param animators A variable number of pairs of model parts and their keyframe lists.
     * @return A looping animation instance.
     */
    protected fun loopAnimation(
        length: Long,
        vararg animators: Pair<ModelPart, BaseAnimation.Timeline>,
    ) = ModelAnimation(this, BaseAnimation.Type.Loop, length, animators.toMap())

    /**
     * Creates a hold animation for the specified model parts, maintaining the last frame when finished.
     *
     * @param length The duration of the animation in ticks.
     * @param animators A variable number of pairs of model parts and their keyframe lists.
     * @return A hold animation instance.
     */
    protected fun holdAnimation(
        length: Long,
        vararg animators: Pair<ModelPart, BaseAnimation.Timeline>,
    ) = ModelAnimation(this, BaseAnimation.Type.Hold, length, animators.toMap())

    /**
     * Creates an animation that plays once for the specified model parts.
     *
     * @param length The duration of the animation in ticks.
     * @param animators A variable number of pairs of model parts and their keyframe lists.
     * @return An instance of a one-time animation.
     */
    protected fun onceAnimation(
        length: Long,
        vararg animators: Pair<ModelPart, BaseAnimation.Timeline>,
    ) = ModelAnimation(this, BaseAnimation.Type.Once, length, animators.toMap())

    protected inline fun timeline(
        part: ModelPart,
        action: BaseAnimation.Timeline.Builder.() -> Unit,
    ) = part to
        BaseAnimation.Timeline
            .Builder()
            .apply(action)
            .build()

    /**
     * Spawns all model parts at the specified location for the given player.
     *
     * @param player The player for whom the model will be visible.
     * @param location The location where the model will be spawned.
     */
    fun spawn(
        player: Player,
        location: Location,
    ) {
        parts.forEach { part ->
            part.entity.spawn(player, location)
        }
    }

    /**
     * Removes all model parts for the specified player.
     *
     * @param player The player from whom the model parts will be removed.
     */
    fun remove(player: Player) {
        parts.forEach { part ->
            part.entity.remove(player)
        }
    }

    /**
     * Checks if an animation is currently playing for the specified player.
     *
     * @param player The player to check.
     * @return True if an animation is playing, false otherwise.
     */
    fun isPlay(player: Player) = playTasks.contains(player.uniqueId)

    /**
     * Checks if the specified animation is currently playing for the given player.
     *
     * @param player The player to check.
     * @param animation The animation to verify.
     * @return True if the specified animation is playing, false otherwise.
     */
    fun isPlay(
        player: Player,
        animation: ModelAnimation,
    ) = getPlay(player) == animation

    /**
     * Retrieves the animation currently playing for the specified player.
     *
     * @param player The player for whom the animation is being retrieved.
     * @return The currently playing animation or null if none is playing.
     */
    fun getPlay(player: Player) = playTasks[player.uniqueId]?.animation

    /**
     * Plays the specified animation for the given player.
     *
     * @param player The player for whom the animation should be played.
     * @param animation The animation to be played.
     */
    fun play(
        player: Player,
        animation: ModelAnimation,
    ) {
        reset(player)
        playTasks[player.uniqueId] = AnimationPlayTask(player, animation)
    }

    /**
     * Resets the transform of all model parts and stops any currently playing animation for the player.
     *
     * @param player The player for whom the reset will be performed.
     */
    fun reset(player: Player) {
        playTasks.remove(player.uniqueId)?.cancel()
        parts.forEach { part ->
            part.entity.resetTransform(player)
        }
    }

    /**
     * Resets the animation for the specified player if the given animation is currently playing.
     *
     * @param player The player for whom the animation reset will be performed.
     * @param animation The animation to check and reset.
     */
    fun reset(
        player: Player,
        animation: ModelAnimation,
    ) {
        if (isPlay(player, animation).not()) return
        reset(player)
    }
}
