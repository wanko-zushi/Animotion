package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.Serializable

@Serializable
data class Face(
    val uv: List<Int> = listOf(),
    val texture: Int? = null
)
