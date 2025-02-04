package dev.s7a.animotion

import org.bukkit.Material

data class AnimotionPart(
    val model: Model,
    val position: Position,
    val rotation: Rotation,
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
