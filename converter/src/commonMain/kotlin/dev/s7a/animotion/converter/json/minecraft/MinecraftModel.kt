package dev.s7a.animotion.converter.json.minecraft

import kotlinx.serialization.Serializable

@Serializable
data class MinecraftModel(val parent: String?, val textures: Map<String, String>?)
