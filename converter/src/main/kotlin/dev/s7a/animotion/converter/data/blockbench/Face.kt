package dev.s7a.animotion.converter.data.blockbench

import dev.s7a.animotion.converter.data.common.FaceType
import kotlinx.serialization.Serializable
import dev.s7a.animotion.converter.data.minecraft.model.Face as MinecraftFace

@Serializable
data class Face(
    val uv: List<Double>,
    val texture: Int,
) {
    fun toMinecraftFace(resolution: Resolution): MinecraftFace =
        MinecraftFace(
            uv.mapIndexed { index, value ->
                value * 16 / (if (index % 2 == 0) resolution.width else resolution.height)
            },
            "#$texture",
        )

    companion object {
        fun Map<FaceType, Face>.toMinecraftFaces(resolution: Resolution): Map<FaceType, MinecraftFace> =
            mapValues { (_, value) ->
                value.toMinecraftFace(resolution)
            }
    }
}
