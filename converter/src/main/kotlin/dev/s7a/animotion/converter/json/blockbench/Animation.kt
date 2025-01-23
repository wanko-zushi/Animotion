package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.uuid.UUID

@Serializable
data class Animation(
    val name: String,
    val loop: LoopType,
    val length: Double,
    @SerialName("anim_time_update") val animTimeUpdate: String = "",
    @SerialName("blend_weight") val blendWeight: String = "",
    @SerialName("start_delay") val startDelay: String = "",
    @SerialName("loop_delay") val loopDelay: String = "",
    @SerialName("animators") val animators: Map<UUID, Animator> = mapOf(),
) {
    @Serializable
    enum class LoopType {
        @SerialName("loop")
        Loop,

        @SerialName("once")
        Once,

        @SerialName("hold")
        Hold,
    }
}
