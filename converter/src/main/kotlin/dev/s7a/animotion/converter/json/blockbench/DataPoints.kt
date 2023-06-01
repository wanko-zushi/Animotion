package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.Serializable

@Serializable
data class DataPoints(
    val x: Double? = null,
    val y: Double? = null,
    val z: Double? = null,
)
