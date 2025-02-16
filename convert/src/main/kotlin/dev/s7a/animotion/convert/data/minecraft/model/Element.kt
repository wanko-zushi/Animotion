package dev.s7a.animotion.convert.data.minecraft.model

import dev.s7a.animotion.convert.data.common.FaceType
import kotlinx.serialization.Serializable

@Serializable
data class Element(
    val from: List<Double>,
    val to: List<Double>,
    val rotation: Rotation,
    val faces: Map<FaceType, Face>,
)
