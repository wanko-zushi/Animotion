package dev.s7a.animotion

import dev.s7a.animotion.data.Animation
import dev.s7a.animotion.data.Keyframe
import dev.s7a.animotion.data.Part
import dev.s7a.animotion.internal.PartEntity
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.util.Vector

abstract class AnimotionModel(
    private val animotion: Animotion,
) {
    private val parts = mutableMapOf<Part, PartEntity>()

    private fun register(part: Part) = parts.put(part, PartEntity(animotion))

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
    ) = Animation(Animation.Type.Loop, length, animators.toMap())

    fun holdAnimation(
        length: Double,
        vararg animators: Pair<Part, List<Pair<Double, Keyframe>>>,
    ) = Animation(Animation.Type.Hold, length, animators.toMap())

    fun onceAnimation(
        length: Double,
        vararg animators: Pair<Part, List<Pair<Double, Keyframe>>>,
    ) = Animation(Animation.Type.Once, length, animators.toMap())

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
}
