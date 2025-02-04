package dev.s7a.animotion

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

abstract class AnimotionModel(
    private val animotion: Animotion,
) {
    private val parts = mutableMapOf<AnimotionPart, AnimotionPartEntity>()

    private fun register(part: AnimotionPart) = parts.put(part, AnimotionPartEntity(animotion))

    fun part(
        itemModel: String,
        position: Position = Position.Zero,
        rotation: Rotation = Rotation.Zero,
    ) = AnimotionPart(AnimotionPart.Model.ItemModel(itemModel), position, rotation).apply(::register)

    fun part(
        material: Material,
        customModelData: Int,
        position: Position = Position.Zero,
        rotation: Rotation = Rotation.Zero,
    ) = AnimotionPart(AnimotionPart.Model.CustomModelData(material, customModelData), position, rotation).apply(::register)

    fun part(
        itemModel: String,
        material: Material,
        customModelData: Int,
        position: Position = Position.Zero,
        rotation: Rotation = Rotation.Zero,
    ) = AnimotionPart(AnimotionPart.Model.Both(itemModel, material, customModelData), position, rotation).apply(::register)

    fun spawn(
        location: Location,
        player: Player,
    ) {
        parts.forEach { (part, entity) ->
            entity.spawn(location, player, part)
        }
    }
}
