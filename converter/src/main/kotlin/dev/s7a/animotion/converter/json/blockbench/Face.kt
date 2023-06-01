package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Face(
    val uv: List<Int>,
    val texture: Int, // TODO BlockBenchModel#textures の番号
) {
    @Serializable
    enum class Type {
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
}
