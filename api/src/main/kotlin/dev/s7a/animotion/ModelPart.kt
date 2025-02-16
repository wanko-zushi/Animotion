package dev.s7a.animotion

import dev.s7a.animotion.common.BasePart
import dev.s7a.animotion.common.Vector3
import dev.s7a.animotion.internal.PartEntity

class ModelPart(
    val parent: AnimotionModel,
    itemModel: String,
    customModelData: Int,
    position: Vector3,
    rotation: Vector3,
) : BasePart(itemModel, customModelData, position, rotation) {
    internal val entity = PartEntity(this)
}
