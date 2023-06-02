package dev.s7a.animotion.converter.json.minecraft.item

import kotlinx.serialization.Serializable

@Serializable
data class MinecraftItem(
    val parent: String? = null,
    val textures: Map<String, String>? = null,
    val overrides: List<Override>? = null,
)
