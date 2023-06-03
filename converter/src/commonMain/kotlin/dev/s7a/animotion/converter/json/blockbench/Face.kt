package dev.s7a.animotion.converter.json.blockbench

import dev.s7a.animotion.converter.json.common.FaceType
import kotlinx.serialization.Serializable
import dev.s7a.animotion.converter.json.minecraft.model.Face as MinecraftFace

@Serializable
data class Face(
    val uv: List<Double>,
    val texture: Int,
) {
    fun toMinecraftFace(): MinecraftFace {
        return MinecraftFace(uv.map { it / 4 }, "#$texture") // TODO なぜか４で割る。何かの数字を使っている？？
    }

    companion object {
        fun Map<FaceType, Face>.toMinecraftFaces(): Map<FaceType, MinecraftFace> {
            return mapValues { (_, value) ->
                value.toMinecraftFace()
            }
        }
    }
}
