package dev.s7a.animotion.converter.json.blockbench

import dev.s7a.animotion.converter.json.common.FaceType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.uuid.UUID

@Serializable
data class Element(
    val from: List<Double>, // TODO MinecraftModel#Elements: from
    val to: List<Double>, // TODO MinecraftModel#Elements: to
    val origin: List<Double>, // TODO MinecraftModel#Elements: origin
    val faces: Map<FaceType, Face>, // TODO MinecraftModel#Elements: faces
    val type: Type,
    val uuid: UUID,
) {
    @Serializable
    enum class Type {
        @SerialName("cube")
        Cube,
    }
}
