package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Animations(
    val uuid: String? = null,
    val name: String? = null,
    val loop: String? = null,
    val override: Boolean? = null,
    val length: Double? = null,
    val snapping: Int? = null,
    val selected: Boolean? = null,
    @SerialName("anim_time_update") val animTimeUpdate: String? = null,
    @SerialName("blend_weight") val blendWeight: String? = null,
    @SerialName("start_delay") val startDelay: String? = null,
    @SerialName("loop_delay") val loopDelay: String? = null,
    @SerialName("animators") val animators: Map<String, Animator> = mapOf(),
)
