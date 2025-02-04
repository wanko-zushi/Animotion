package dev.s7a.animotion.spigot

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

abstract class AnimotionModel(
    private val animotion: Animotion,
) {
    private val parts = mutableMapOf<AnimotionPart, AnimotionPartEntity>()

    private fun register(part: AnimotionPart) = parts.put(part, AnimotionPartEntity(animotion))

    fun part(
        material: Material,
        model: Int,
        position: Position = Position.Zero,
        rotation: Rotation = Rotation.Zero,
    ) = AnimotionPart(material, model, position, rotation).apply(::register)

    fun spawn(
        location: Location,
        player: Player,
    ) {
        parts.forEach { (part, entity) ->
            entity.spawn(location, player, part)
        }
    }
}
