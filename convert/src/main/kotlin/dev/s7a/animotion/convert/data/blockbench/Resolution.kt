package dev.s7a.animotion.convert.data.blockbench

import kotlinx.serialization.Serializable

@Serializable
data class Resolution(
    val width: Int,
    val height: Int,
)
