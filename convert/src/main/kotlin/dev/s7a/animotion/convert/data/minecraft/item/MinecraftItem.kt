package dev.s7a.animotion.convert.data.minecraft.item

import kotlinx.serialization.Serializable

@Serializable
data class MinecraftItem(
    val parent: String,
    val textures: Map<String, String>,
    val overrides: List<Override>,
)
