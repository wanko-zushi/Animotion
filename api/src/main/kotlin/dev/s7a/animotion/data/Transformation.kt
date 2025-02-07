package dev.s7a.animotion.data

import org.bukkit.util.Vector

data class Transformation(
    val translation: Vector? = null,
    val scale: Vector? = null,
    val rotation: Vector? = null,
)
