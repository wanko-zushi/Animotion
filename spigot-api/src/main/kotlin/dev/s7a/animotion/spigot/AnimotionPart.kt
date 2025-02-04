package dev.s7a.animotion.spigot

import org.bukkit.Material

data class AnimotionPart(
    val material: Material,
    val model: Int,
    val position: Position,
    val rotation: Rotation,
)
