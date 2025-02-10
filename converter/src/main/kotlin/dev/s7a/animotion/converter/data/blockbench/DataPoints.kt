package dev.s7a.animotion.converter.data.blockbench

import kotlinx.serialization.Serializable

@Serializable
data class DataPoints(
    val x: Double,
    val y: Double,
    val z: Double,
)
