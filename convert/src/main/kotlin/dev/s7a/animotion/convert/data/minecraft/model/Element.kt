package dev.s7a.animotion.convert.data.minecraft.model

import dev.s7a.animotion.convert.data.common.FaceType
import dev.s7a.animotion.convert.model.Vector3
import kotlinx.serialization.Serializable

@Serializable
data class Element(
    val from: Vector3,
    val to: Vector3,
    val rotation: Rotation,
    val faces: Map<FaceType, Face>,
)
