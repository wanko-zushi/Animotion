package dev.s7a.animotion.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public enum class AnimationLoopType {
    @SerialName("loop")
    Loop,

    @SerialName("once")
    Once,

    @SerialName("hold")
    Hold,
}
