package dev.s7a.animotion.model

import kotlinx.serialization.Serializable

@Serializable
public data class Part<Material>(val type: Material, val model: Int, val position: Position)
