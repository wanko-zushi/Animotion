package dev.s7a.animotion.data

import dev.s7a.animotion.Animotion
import dev.s7a.animotion.internal.PartEntity
import org.bukkit.Material
import org.bukkit.util.Vector

class Part internal constructor(
    animotion: Animotion,
    val model: Model,
    val position: Vector,
    val rotation: Vector,
) {
    internal val entity = PartEntity(animotion, this)

    sealed interface Model {
        data class ItemModel(
            val itemModel: String,
        ) : Model

        data class CustomModelData(
            val material: Material,
            val customModelData: Int,
        ) : Model

        data class Both(
            val itemModel: String,
            val material: Material,
            val customModelData: Int,
        ) : Model
    }
}
