package dev.s7a.animotion.data

import org.bukkit.Material
import org.bukkit.util.Vector

data class Part(
    val model: Model,
    val position: Vector,
    val rotation: Vector,
) {
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
