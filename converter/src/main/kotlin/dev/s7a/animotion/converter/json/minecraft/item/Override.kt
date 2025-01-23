package dev.s7a.animotion.converter.json.minecraft.item

import kotlinx.serialization.Serializable

@Serializable
data class Override(val predicate: Predicate, val model: String)
