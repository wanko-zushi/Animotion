package dev.s7a.animotion.model

import kotlinx.serialization.Serializable

@Serializable
public data class AnimatorFrame(
    val type: AnimatorFrameType,
    val point: Vector3,
    val time: Double,
)
