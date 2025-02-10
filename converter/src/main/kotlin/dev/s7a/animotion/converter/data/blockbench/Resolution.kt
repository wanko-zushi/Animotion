package dev.s7a.animotion.converter.data.blockbench

import kotlinx.serialization.Serializable

@Serializable
data class Resolution(
    val width: Int,
    val height: Int,
)
