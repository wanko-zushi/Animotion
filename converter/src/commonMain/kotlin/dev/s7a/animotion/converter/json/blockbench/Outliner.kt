package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.Serializable

@Serializable
data class Outliner(
    val name: String,
    val origin: List<Double>,
    val rotation: List<Double>? = null,
    val uuid: String,
    val children: List<String>, // TODO Elements#uuid
)
