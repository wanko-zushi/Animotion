package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.Serializable

@Serializable
data class Faces(
    val north: Face? = Face(),
    val east: Face? = Face(),
    val south: Face? = Face(),
    val west: Face? = Face(),
    val up: Face? = Face(),
    val down: Face? = Face(),
)
