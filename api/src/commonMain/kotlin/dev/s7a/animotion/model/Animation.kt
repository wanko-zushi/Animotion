package dev.s7a.animotion.model

import kotlinx.serialization.Serializable

@Serializable
public data class Animation(val type: AnimationType, val frames: List<AnimationFrame>)
