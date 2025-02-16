package dev.s7a.animotion

import dev.s7a.animotion.common.BaseAnimation
import dev.s7a.animotion.common.Vector3
import dev.s7a.animotion.internal.AnimationPlayTask
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.UUID

abstract class AnimotionModel(
    internal val animotion: Animotion,
    val baseScale: Float,
) {
    private val parts = mutableListOf<ModelPart>()
    private val playTasks = mutableMapOf<UUID, AnimationPlayTask>()

    protected fun part(
        itemModel: String,
        customModelData: Int,
        position: Vector3 = Vector3(),
        rotation: Vector3 = Vector3(),
    ) = ModelPart(this, itemModel, customModelData, position, rotation).apply(parts::add)

    protected fun loopAnimation(
        length: Double,
        vararg animators: Pair<ModelPart, List<Pair<Double, BaseAnimation.Keyframe>>>,
    ) = ModelAnimation(this, BaseAnimation.Type.Loop, length, animators.toMap())

    protected fun holdAnimation(
        length: Double,
        vararg animators: Pair<ModelPart, List<Pair<Double, BaseAnimation.Keyframe>>>,
    ) = ModelAnimation(this, BaseAnimation.Type.Hold, length, animators.toMap())

    protected fun onceAnimation(
        length: Double,
        vararg animators: Pair<ModelPart, List<Pair<Double, BaseAnimation.Keyframe>>>,
    ) = ModelAnimation(this, BaseAnimation.Type.Once, length, animators.toMap())

    protected fun position(
        x: Double,
        y: Double,
        z: Double,
    ) = BaseAnimation.Keyframe(BaseAnimation.Keyframe.Channel.Position, Vector3(x, y, z))

    protected fun rotation(
        x: Double,
        y: Double,
        z: Double,
    ) = BaseAnimation.Keyframe(BaseAnimation.Keyframe.Channel.Rotation, Vector3(x, y, z))

    protected fun scale(
        x: Double,
        y: Double,
        z: Double,
    ) = BaseAnimation.Keyframe(BaseAnimation.Keyframe.Channel.Scale, Vector3(x, y, z))

    fun spawn(
        player: Player,
        location: Location,
    ) {
        parts.forEach { part ->
            part.entity.spawn(player, location)
        }
    }

    fun remove(player: Player) {
        parts.forEach { part ->
            part.entity.remove(player)
        }
    }

    fun isPlay(player: Player) = playTasks.contains(player.uniqueId)

    fun isPlay(
        player: Player,
        animation: ModelAnimation,
    ) = getPlay(player) == animation

    fun getPlay(player: Player) = playTasks[player.uniqueId]?.animation

    fun play(
        player: Player,
        animation: ModelAnimation,
    ) {
        reset(player)
        playTasks[player.uniqueId] = AnimationPlayTask(player, animation)
    }

    fun reset(player: Player) {
        playTasks.remove(player.uniqueId)?.cancel()
        parts.forEach { part ->
            part.entity.resetTransform(player)
        }
    }

    fun reset(
        player: Player,
        animation: ModelAnimation,
    ) {
        if (isPlay(player, animation).not()) return
        reset(player)
    }
}
