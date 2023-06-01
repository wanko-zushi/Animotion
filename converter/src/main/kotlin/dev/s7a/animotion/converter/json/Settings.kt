package dev.s7a.animotion.converter.json

import kotlinx.serialization.Serializable

@Serializable
data class Settings(val items: Map<String, List<String>>)
