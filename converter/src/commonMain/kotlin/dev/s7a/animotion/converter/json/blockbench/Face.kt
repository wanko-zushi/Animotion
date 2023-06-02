package dev.s7a.animotion.converter.json.blockbench

import dev.s7a.animotion.converter.json.common.FaceType
import kotlinx.serialization.Serializable
import dev.s7a.animotion.converter.json.minecraft.Face as MinecraftFace

@Serializable
data class Face(
    val uv: List<Double>,
    val texture: Int,
) {
    fun toMinecraftFace(): MinecraftFace {
        return MinecraftFace(uv, "#$texture")
    }

    companion object {
        fun Map<FaceType, Face>.toMinecraftFaces(): Map<FaceType, MinecraftFace> {
            return entries.associate { (key, value) ->
                key to value.toMinecraftFace()
            }
        }
    }
}
