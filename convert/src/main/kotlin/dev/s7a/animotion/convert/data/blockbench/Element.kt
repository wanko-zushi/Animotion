package dev.s7a.animotion.convert.data.blockbench

import dev.s7a.animotion.convert.data.blockbench.Face.Companion.toMinecraftFaces
import dev.s7a.animotion.convert.data.common.FaceType
import dev.s7a.animotion.convert.data.minecraft.model.Rotation
import dev.s7a.animotion.convert.util.eachMinus
import dev.s7a.animotion.convert.util.eachPlus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import dev.s7a.animotion.convert.data.minecraft.model.Element as MinecraftElement

@Serializable
@OptIn(ExperimentalUuidApi::class)
data class Element(
    val from: List<Double>,
    val to: List<Double>,
    val rotation: List<Double> = listOf(0.0, 0.0, 0.0),
    val origin: List<Double>,
    val faces: Map<FaceType, Face>,
    val type: Type,
    val uuid: Uuid,
) {
    @Serializable
    enum class Type {
        @SerialName("cube")
        Cube,
    }

    private fun rotationAngle(outliner: Outliner): Pair<Double, Rotation.Axis> {
        val rotation = rotation.eachMinus(outliner.rotation)
        val index = rotation.indexOfFirst { it != 0.0 }
        if (index == -1) return 0.0 to Rotation.Axis.Y
        return rotation[index] to Rotation.Axis.entries[index]
    }

    fun toMinecraftElement(
        outliner: Outliner,
        textures: List<Texture>,
    ): MinecraftElement {
        val (angle, axis) = rotationAngle(outliner)
        val center = listOf(8.0, 8.0, 8.0)
        return MinecraftElement(
            from.eachPlus(center).eachMinus(outliner.origin),
            to.eachPlus(center).eachMinus(outliner.origin),
            Rotation(angle, axis, origin.eachPlus(center).eachMinus(outliner.origin)),
            faces.toMinecraftFaces(textures),
        )
    }

    companion object {
        fun List<Element>.toMinecraftElements(
            outliner: Outliner,
            textures: List<Texture>,
        ): List<MinecraftElement> =
            map {
                it.toMinecraftElement(outliner, textures)
            }
    }
}
