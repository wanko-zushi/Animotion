package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Keyframes(
    val channel: String? = null,
    @SerialName("data_points") val dataPoints: List<DataPoints> = listOf(),
    val uuid: String? = null,
    val time: Double? = null,
    val color: Int? = null,
    val interpolation: String? = null,
    @SerialName("bezier_linked") val bezierLinked: Boolean? = null,
    @SerialName("bezier_left_time") val bezierLeftTime: List<Double> = listOf(),
    @SerialName("bezier_left_value") val bezierLeftValue: List<Int> = listOf(),
    @SerialName("bezier_right_time") val bezierRightTime: List<Double> = listOf(),
    @SerialName("bezier_right_value") val bezierRightValue: List<Int> = listOf(),
)
