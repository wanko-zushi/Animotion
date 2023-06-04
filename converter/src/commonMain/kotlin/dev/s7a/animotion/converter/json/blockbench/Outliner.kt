package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.Serializable
import kotlinx.uuid.UUID

@Serializable
data class Outliner(
    val name: String,
    val origin: List<Double>, // TODO .anim に出力
    val rotation: List<Double>? = null, // TODO .anim に出力
    val uuid: UUID,
    val children: List<UUID>,
)
