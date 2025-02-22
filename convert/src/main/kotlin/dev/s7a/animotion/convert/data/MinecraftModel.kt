package dev.s7a.animotion.convert.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinecraftModel(
    @SerialName("texture_size") val textureSize: List<Int>,
    val textures: Map<String, String>,
    val elements: List<Element>,
) {
    @Serializable
    data class Element(
        val from: Vector3,
        val to: Vector3,
        val rotation: Rotation,
        val faces: Map<FaceType, Face>,
    ) {
        @Serializable
        data class Rotation(
            val angle: Double,
            val axis: Axis,
            val origin: Vector3,
        ) {
            @Serializable
            enum class Axis {
                @SerialName("x")
                X,

                @SerialName("y")
                Y,

                @SerialName("z")
                Z,
            }
        }

        @Serializable
        enum class FaceType {
            @SerialName("north")
            North,

            @SerialName("east")
            East,

            @SerialName("south")
            South,

            @SerialName("west")
            West,

            @SerialName("up")
            Up,

            @SerialName("down")
            Down,
        }

        @Serializable
        data class Face(
            val uv: List<Double>,
            val texture: String,
        )
    }
}
