package dev.s7a.animotion.convert.data.blockbench

import kotlinx.serialization.Serializable

@Serializable
data class DataPoints(
    val x: Double,
    val y: Double,
    val z: Double,
)
