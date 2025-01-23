package dev.s7a.animotion.converter.json.animotion

import kotlinx.serialization.Serializable

@Serializable
data class AnimotionSettings(
    val namespace: String = "animotion",
    val items: Map<String, List<String>>,
)
