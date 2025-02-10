package dev.s7a.animotion

import dev.s7a.animotion.data.Animation
import dev.s7a.animotion.data.Keyframe
import dev.s7a.animotion.data.Part
import dev.s7a.animotion.exception.PartNotFoundException
import dev.s7a.animotion.internal.AnimationPlayTask
import dev.s7a.animotion.internal.PartEntity
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.UUID

abstract class AnimotionModel(
    internal val animotion: Animotion,
) {
    private val parts = mutableMapOf<Part, PartEntity>()
    private val playTasks = mutableMapOf<UUID, AnimationPlayTask>()

    private fun register(part: Part) = parts.put(part, PartEntity(animotion))

    internal fun get(part: Part) = parts[part] ?: throw PartNotFoundException()

    fun part(
        itemModel: String,
        position: Vector = Vector(),
        rotation: Vector = Vector(),
    ) = Part(Part.Model.ItemModel(itemModel), position, rotation).apply(::register)

    fun part(
        material: Material,
        customModelData: Int,
        position: Vector = Vector(),
        rotation: Vector = Vector(),
    ) = Part(Part.Model.CustomModelData(material, customModelData), position, rotation).apply(::register)

    fun part(
        itemModel: String,
        material: Material,
        customModelData: Int,
        position: Vector = Vector(),
        rotation: Vector = Vector(),
    ) = Part(Part.Model.Both(itemModel, material, customModelData), position, rotation).apply(::register)

    fun loopAnimation(
        length: Double,
        vararg animators: Pair<Part, List<Pair<Double, Keyframe>>>,
    ) = Animation(this, Animation.Type.Loop, length, animators.toMap())

    fun holdAnimation(
        length: Double,
        vararg animators: Pair<Part, List<Pair<Double, Keyframe>>>,
    ) = Animation(this, Animation.Type.Hold, length, animators.toMap())

    fun onceAnimation(
        length: Double,
        vararg animators: Pair<Part, List<Pair<Double, Keyframe>>>,
    ) = Animation(this, Animation.Type.Once, length, animators.toMap())

    fun position(
        x: Double,
        y: Double,
        z: Double,
    ) = Keyframe(Keyframe.Channel.Position, Vector(x, y, z))

    fun rotation(
        x: Double,
        y: Double,
        z: Double,
    ) = Keyframe(Keyframe.Channel.Rotation, Vector(x, y, z))

    fun scale(
        x: Double,
        y: Double,
        z: Double,
    ) = Keyframe(Keyframe.Channel.Scale, Vector(x, y, z))

    fun spawn(
        player: Player,
        location: Location,
    ) {
        parts.forEach { (part, entity) ->
            entity.spawn(player, location, part)
        }
    }

    fun isPlay(player: Player) = playTasks.contains(player.uniqueId)

    fun isPlay(
        player: Player,
        animation: Animation,
    ) = getPlay(player) == animation

    fun getPlay(player: Player) = playTasks[player.uniqueId]?.animation

    fun play(
        player: Player,
        animation: Animation,
    ) {
        reset(player)
        playTasks[player.uniqueId] = AnimationPlayTask(player, animation)
    }

    fun reset(player: Player) {
        playTasks.remove(player.uniqueId)?.cancel()
        parts.forEach { (part, entity) ->
            entity.resetTransform(player, part)
        }
    }

    fun reset(
        player: Player,
        animation: Animation,
    ) {
        if (isPlay(player, animation).not()) return
        reset(player)
    }
}
