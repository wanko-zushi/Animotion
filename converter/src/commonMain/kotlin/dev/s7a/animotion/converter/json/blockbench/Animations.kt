package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.uuid.UUID

@Serializable
data class Animations(
    val name: String, // TODO アニメーション名
    val loop: LoopType, // TODO 再生方法
    val length: Double, // TODO 再生時間(s)
    @SerialName("anim_time_update") val animTimeUpdate: String = "", // TODO 非対応なので "" であることをチェック
    @SerialName("blend_weight") val blendWeight: String = "", // TODO 非対応なので "" であることをチェック
    @SerialName("start_delay") val startDelay: String = "", // TODO 非対応なので "" であることをチェック
    @SerialName("loop_delay") val loopDelay: String = "", // TODO 非対応なので "" であることをチェック
    @SerialName("animators") val animators: Map<UUID, Animator> = mapOf(), // TODO K: Outliner#uuid
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
