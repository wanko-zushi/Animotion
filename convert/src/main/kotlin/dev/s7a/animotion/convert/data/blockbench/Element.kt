package dev.s7a.animotion.convert.data.blockbench

import dev.s7a.animotion.convert.data.blockbench.Face.Companion.toMinecraftFaces
import dev.s7a.animotion.convert.data.common.FaceType
import dev.s7a.animotion.convert.data.minecraft.model.Rotation
import dev.s7a.animotion.convert.model.Vector3
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import dev.s7a.animotion.convert.data.minecraft.model.Element as MinecraftElement

@Serializable
@OptIn(ExperimentalUuidApi::class)
data class Element(
    val from: Vector3,
    val to: Vector3,
    val rotation: Vector3 = Vector3(),
    val origin: Vector3,
    val faces: Map<FaceType, Face>,
    val type: Type,
    val uuid: Uuid,
) {
    @Serializable
    enum class Type {
        @SerialName("cube")
        Cube,
    }

    fun toMinecraftElement(
        outliner: Outliner,
        textures: List<Texture>,
    ): MinecraftElement {
        val rotation = rotation - outliner.rotation
        val (angle, axis) =
            when {
                rotation.x != 0.0 -> rotation.x to Rotation.Axis.X
                rotation.y != 0.0 -> rotation.y to Rotation.Axis.Y
                rotation.z != 0.0 -> rotation.z to Rotation.Axis.Z
                else -> 0.0 to Rotation.Axis.Y
            }
        val center = Vector3(8.0)
        return MinecraftElement(
            from + center - outliner.origin,
            to + center - outliner.origin,
            Rotation(angle, axis, origin + center - outliner.origin),
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
