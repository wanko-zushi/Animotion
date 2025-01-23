package dev.s7a.animotion.converter.json.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
