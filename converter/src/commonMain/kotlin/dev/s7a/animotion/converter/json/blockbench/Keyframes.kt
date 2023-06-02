package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Keyframes(
    val channel: Channel,
    @SerialName("data_points") val dataPoints: List<DataPoints>,
    val time: Double,
    val interpolation: Interpolation,
    @SerialName("bezier_linked") val bezierLinked: Boolean,
    @SerialName("bezier_left_time") val bezierLeftTime: List<Double>,
    @SerialName("bezier_left_value") val bezierLeftValue: List<Int>,
    @SerialName("bezier_right_time") val bezierRightTime: List<Double>,
    @SerialName("bezier_right_value") val bezierRightValue: List<Int>,
) {
    @Serializable
    enum class Channel {
        @SerialName("rotation")
        Rotation,

        @SerialName("position")
        Position,

        @SerialName("scale")
        Scale,
    }

    @Serializable
    enum class Interpolation {
        @SerialName("linear")
        Linear,
    }
}
