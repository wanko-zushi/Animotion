package dev.s7a.animotion

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

    fun spawn(
        location: Location,
        player: Player,
    ) {
        parts.forEach { (part, entity) ->
            entity.spawn(location, player, part)
        }
    }
}
