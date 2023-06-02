package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.Serializable

@Serializable
data class Animator(
    val name: String, // TODO Outliner#name と同じ
    val type: String, // TODO bone であることをチェック
    val keyframes: List<Keyframes>,
)
