package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Elements(
    val name: String? = null,
    @SerialName("box_uv") val boxUv: Boolean? = null,
    val rescale: Boolean? = null,
    val locked: Boolean? = null,
    val from: List<Double> = listOf(),
    val to: List<Double> = listOf(),
    val autouv: Int? = null,
    val color: Int? = null,
    val origin: List<Double> = listOf(),
    val faces: Faces? = Faces(),
    val type: String? = null,
    val uuid: String? = null,
)
