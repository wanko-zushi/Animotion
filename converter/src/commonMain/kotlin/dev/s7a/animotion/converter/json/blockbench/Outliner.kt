package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.Serializable
import kotlinx.uuid.UUID

@Serializable
data class Outliner(
    val name: String,
    // TODO .anim に出力
    val origin: List<Double>,
    // TODO .anim に出力
    val rotation: List<Double>? = null,
    val uuid: UUID,
    val children: List<UUID>,
)
