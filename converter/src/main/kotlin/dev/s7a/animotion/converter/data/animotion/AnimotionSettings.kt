package dev.s7a.animotion.converter.data.animotion

import kotlinx.serialization.Serializable

@Serializable
data class AnimotionSettings(
    val namespace: String = "animotion",
    val item: Item = Item("stick", "STICK"),
    val `package`: String = "",
) {
    @Serializable
    data class Item(
        val minecraft: String,
        val bukkit: String,
    )
}
