package dev.s7a.animotion.converter.json.blockbench

import dev.s7a.animotion.converter.json.blockbench.Face.Companion.toMinecraftFaces
import dev.s7a.animotion.converter.json.common.FaceType
import dev.s7a.animotion.converter.json.minecraft.model.Rotation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.uuid.UUID
import dev.s7a.animotion.converter.json.minecraft.model.Element as MinecraftElement

@Serializable
data class Element(
    val from: List<Double>,
    val to: List<Double>,
    val origin: List<Double>,
    val faces: Map<FaceType, Face>,
    val type: Type,
    val uuid: UUID,
) {
    @Serializable
    enum class Type {
        @SerialName("cube")
        Cube,
    }

    fun toMinecraftElement(): MinecraftElement {
        return MinecraftElement(from, to, Rotation(0.0, "y", origin), faces.toMinecraftFaces())
    }

    companion object {
        fun List<Element>.toMinecraftElements(): List<MinecraftElement> {
            return map(Element::toMinecraftElement)
        }
    }
}
