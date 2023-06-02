package dev.s7a.animotion.converter.json.minecraft

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinecraftModel(
    val parent: String? = null,
    @SerialName("texture_size") val textureSize: List<Int>? = null,
    val textures: Map<String, String>? = null,
    val elements: List<Element>? = null
)
