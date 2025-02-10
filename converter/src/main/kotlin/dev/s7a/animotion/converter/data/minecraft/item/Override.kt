package dev.s7a.animotion.converter.data.minecraft.item

import kotlinx.serialization.Serializable

@Serializable
data class Override(
    val predicate: Predicate,
    val model: String,
)
