package dev.s7a.animotion.converter.json.blockbench

import dev.s7a.animotion.converter.json.blockbench.Face.Companion.toMinecraftFaces
import dev.s7a.animotion.converter.json.common.FaceType
import dev.s7a.animotion.converter.json.minecraft.model.Rotation
import dev.s7a.animotion.converter.util.eachMinus
import dev.s7a.animotion.converter.util.eachPlus
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

    private fun rotationAngle(outliner: Outliner): Pair<Double, Rotation.Axis> {
        val rotation = rotation?.eachMinus(outliner.rotation ?: List(rotation.size) { 0.0 }) ?: return 0.0 to Rotation.Axis.Y
        val index = rotation.indexOfFirst { it != 0.0 }
        if (index == -1) return 0.0 to Rotation.Axis.Y
        return rotation[index] to Rotation.Axis.entries[index]
    }

    fun toMinecraftElement(
        outliner: Outliner,
        resolution: Resolution,
    ): MinecraftElement {
        val (angle, axis) = rotationAngle(outliner)
        val center = listOf(8.0, 0.0, 8.0)
        return MinecraftElement(
            from.eachPlus(center).eachMinus(outliner.origin),
            to.eachPlus(center).eachMinus(outliner.origin),
            Rotation(angle, axis, origin.eachPlus(center).eachMinus(outliner.origin)),
            faces.toMinecraftFaces(resolution),
        )
    }

    companion object {
        fun List<Element>.toMinecraftElements(
            outliner: Outliner,
            resolution: Resolution,
        ): List<MinecraftElement> =
            map {
                it.toMinecraftElement(outliner, resolution)
            }
    }
}
