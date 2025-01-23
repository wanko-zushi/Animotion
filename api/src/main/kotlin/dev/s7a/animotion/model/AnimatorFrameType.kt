package dev.s7a.animotion.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public enum class AnimatorFrameType {
    @SerialName("rotation")
    Rotation,

    @SerialName("position")
    Position,

    @SerialName("scale")
    Scale,
}
