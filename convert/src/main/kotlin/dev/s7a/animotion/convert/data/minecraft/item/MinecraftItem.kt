package dev.s7a.animotion.convert.data.minecraft.item

import kotlinx.serialization.Serializable

@Serializable
data class MinecraftItem(
    val parent: String? = null,
    val textures: Map<String, String>? = null,
    val overrides: List<Override>? = null,
)
