package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.Serializable

@Serializable
data class Animator(
    val name: String? = null,
    val type: String? = null,
    val keyframes: List<Keyframes> = listOf(),
)
