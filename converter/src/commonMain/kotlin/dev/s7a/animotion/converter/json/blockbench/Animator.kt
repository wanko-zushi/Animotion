package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Animator(
    val name: String,
    val type: Type,
    val keyframes: List<Keyframes>,
) {
    @Serializable
    enum class Type {
        @SerialName("bone")
        Bone,
    }
}
