package dev.s7a.animotion.model

import kotlinx.serialization.Serializable

@Serializable
public data class Animator(val frames: List<AnimatorFrame>)
