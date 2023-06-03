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

    private fun rotationAngle(rotationOffset: List<Double>): Pair<Double, Rotation.Axis> {
        val rotation = (this.rotation ?: listOf(0.0, 0.0, 0.0)).mapIndexed { index, value ->
            value + rotationOffset[index]
        }
        val index = rotation.indexOfFirst { it != 0.0 }
        if (index == -1) return 0.0 to Rotation.Axis.Y
        return rotation[index] to Rotation.Axis.values()[index]
    }

    fun toMinecraftElement(originOffset: List<Double>, rotationOffset: List<Double>?): MinecraftElement {
        val (angle, axis) = rotationAngle(rotationOffset ?: listOf(0.0, 0.0, 0.0))
        val origin = origin.mapIndexed { index, value ->
            value + originOffset[index]
        }
        return MinecraftElement(from, to, Rotation(angle, axis, origin), faces.toMinecraftFaces())
    }

    companion object {
        fun List<Element>.toMinecraftElements(originOffset: List<Double>, rotationOffset: List<Double>?): List<MinecraftElement> {
            return map {
                it.toMinecraftElement(originOffset, rotationOffset)
            }
        }
    }
}
