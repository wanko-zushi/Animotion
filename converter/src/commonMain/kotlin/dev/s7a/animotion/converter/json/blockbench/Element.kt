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
    val rotation: List<Double>? = null,
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

    private fun rotationAngle(): Pair<Double, Rotation.Axis> {
        val rotation = this.rotation ?: return 0.0 to Rotation.Axis.Y
        val index = rotation.indexOfFirst { it != 0.0 }
        if (index == -1) return 0.0 to Rotation.Axis.Y
        return rotation[index] to Rotation.Axis.values()[index]
    }

    fun toMinecraftElement(resolution: Resolution): MinecraftElement {
        val (angle, axis) = rotationAngle()
        return MinecraftElement(from, to, Rotation(angle, axis, origin), faces.toMinecraftFaces(resolution))
    }

    companion object {
        fun List<Element>.toMinecraftElements(resolution: Resolution): List<MinecraftElement> {
            return map {
                it.toMinecraftElement(resolution)
            }
        }
    }
}
