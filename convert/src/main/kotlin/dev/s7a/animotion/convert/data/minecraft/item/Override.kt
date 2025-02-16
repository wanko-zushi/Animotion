package dev.s7a.animotion.convert.data.minecraft.item

import kotlinx.serialization.Serializable

@Serializable
data class Override(
    val predicate: Predicate,
    val model: String,
)
