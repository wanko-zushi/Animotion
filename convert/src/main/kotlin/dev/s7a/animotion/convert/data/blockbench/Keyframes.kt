package dev.s7a.animotion.convert.data.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Keyframes(
    val channel: Channel,
    @SerialName("data_points") val dataPoints: List<DataPoints>,
    val time: Double,
    val interpolation: Interpolation,
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

        @SerialName("catmullrom")
        Catmullrom,
    }
}
