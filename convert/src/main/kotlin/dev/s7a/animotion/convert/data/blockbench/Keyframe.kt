package dev.s7a.animotion.convert.data.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Keyframe(
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
    data class DataPoints(
        val x: Double,
        val y: Double,
        val z: Double,
    )

    @Serializable
    enum class Interpolation {
        @SerialName("linear")
        Linear,

        @SerialName("catmullrom")
        Catmullrom,
    }
}
