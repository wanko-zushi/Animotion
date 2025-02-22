package dev.s7a.animotion.convert.data.blockbench

import dev.s7a.animotion.convert.data.common.FaceType
import kotlinx.serialization.Serializable
import dev.s7a.animotion.convert.data.minecraft.model.Face as MinecraftFace

@Serializable
data class Face(
    val uv: List<Double>,
    val texture: Int? = null,
) {
    fun toMinecraftFace(textures: List<Texture>): MinecraftFace =
        if (texture == null) {
            MinecraftFace(
                listOf(0.0, 0.0, 0.0, 0.0),
                "#missing",
            )
        } else {
            MinecraftFace(
                uv.mapIndexed { index, value ->
                    value * 16 / (if (index % 2 == 0) textures[texture].uvWidth else textures[texture].uvHeight)
                },
                "#$texture",
            )
        }

    companion object {
        fun Map<FaceType, Face>.toMinecraftFaces(textures: List<Texture>): Map<FaceType, MinecraftFace> =
            mapValues { (_, value) ->
                value.toMinecraftFace(textures)
            }
    }
}
