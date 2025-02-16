package dev.s7a.animotion.common

/**
 * Represents a base part of an animation or model with customizable properties.
 *
 * @property itemModel The model identifier or path for the part.
 * @property customModelData A numerical value indicating a custom model variant.
 * @property position The 3D position of the part in the world or animation.
 * @property rotation The orientation of the part as a 3D vector.
 */
open class BasePart(
    val itemModel: String,
    val customModelData: Int,
    val position: Vector3,
    val rotation: Vector3,
)
