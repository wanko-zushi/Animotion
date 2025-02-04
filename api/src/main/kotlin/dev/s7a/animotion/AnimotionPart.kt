package dev.s7a.animotion

import org.bukkit.Material

data class AnimotionPart(
    val material: Material,
    val model: Int,
    val position: Position,
    val rotation: Rotation,
)
