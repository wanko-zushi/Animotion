package dev.s7a.animotion.model

import kotlinx.serialization.Serializable

@Serializable
public data class Part<Material>(
    val name: String,
    val type: Material,
    val model: Int,
    val position: Vector3,
    val rotation: Vector3 = Vector3.Zero,
)
