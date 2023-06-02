package dev.s7a.animotion.converter.json.minecraft.model

import dev.s7a.animotion.converter.json.common.FaceType
import kotlinx.serialization.Serializable

@Serializable
data class Element(
    val from: List<Double>,
    val to: List<Double>,
    val rotation: Rotation,
    val faces: Map<FaceType, Face>
)
