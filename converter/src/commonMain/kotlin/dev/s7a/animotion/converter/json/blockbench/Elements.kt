package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.Serializable
import kotlinx.uuid.UUID

@Serializable
data class Elements(
    val from: List<Double>, // TODO MinecraftModel#Elements: from
    val to: List<Double>, // TODO MinecraftModel#Elements: to
    val origin: List<Double>, // TODO MinecraftModel#Elements: origin
    val faces: Map<Face.Type, Face>, // TODO MinecraftModel#Elements: faces
    val type: String, // TODO cube であることをチェックする
    val uuid: UUID,
)
