package dev.s7a.animotion.converter.json.animotion

import dev.s7a.animotion.converter.json.minecraft.model.MinecraftModel
import kotlinx.serialization.Serializable

@Serializable
data class Part(val name: String, val model: MinecraftModel)
