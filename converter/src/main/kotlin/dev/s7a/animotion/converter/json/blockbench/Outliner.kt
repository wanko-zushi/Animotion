package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Outliner(
    val name: String? = null,
    val origin: List<Double> = listOf(),
    val rotation: List<Double> = listOf(),
    val color: Int? = null,
    val nbt: String? = null,
    val uuid: String? = null,
    val export: Boolean? = null,
    @SerialName("mirror_uv") val mirrorUv: Boolean? = null,
    val isOpen: Boolean? = null,
    val locked: Boolean? = null,
    val visibility: Boolean? = null,
    val autouv: Int? = null,
    val children: List<String> = listOf(),
)
