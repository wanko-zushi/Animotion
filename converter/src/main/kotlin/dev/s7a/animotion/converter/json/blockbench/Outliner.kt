package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
@OptIn(ExperimentalUuidApi::class)
data class Outliner(
    val name: String,
    val origin: List<Double>,
    val rotation: List<Double>? = null,
    val uuid: Uuid,
    val children: List<Uuid>,
)
