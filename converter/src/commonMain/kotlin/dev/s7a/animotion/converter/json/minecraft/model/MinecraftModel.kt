package dev.s7a.animotion.converter.json.minecraft.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinecraftModel(
    @SerialName("texture_size") val textureSize: List<Int>,
    val textures: Map<String, String>,
    val elements: List<Element>,
)
