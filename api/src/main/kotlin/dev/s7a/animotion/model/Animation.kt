package dev.s7a.animotion.model

import kotlinx.serialization.Serializable

@Serializable
public data class Animation(
    val loopType: AnimationLoopType,
    val length: Double,
    val animators: Map<Int, Animator>,
)
