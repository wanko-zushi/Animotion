package dev.s7a.animotion.converter.json.minecraft

import kotlinx.serialization.Serializable

@Serializable
data class Face(val uv: List<Double>, val texture: String)
