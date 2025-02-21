package dev.s7a.animotion.convert.data.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Animator(
    val name: String,
    val type: Type,
    val keyframes: List<Keyframe>,
) {
    @Serializable
    enum class Type {
        @SerialName("bone")
        Bone,
    }
}
