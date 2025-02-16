package dev.s7a.animotion

import dev.s7a.animotion.common.BasePart
import dev.s7a.animotion.common.Vector3
import dev.s7a.animotion.internal.PartEntity

/**
 * Represents a part of an animation model, defined by properties like position, rotation,
 * item model, and custom model data.
 *
 * @param parent The parent animation model this part belongs to.
 * @param itemModel The item model associated with this part.
 * @param customModelData The CustomModelData value used to render this part.
 * @param position The relative position of this part in the model.
 * @param rotation The rotation of this part in the model.
 */
class ModelPart(
    val parent: AnimotionModel,
    itemModel: String,
    customModelData: Int,
    position: Vector3,
    rotation: Vector3,
) : BasePart(itemModel, customModelData, position, rotation) {
    /**
     * The internal entity used to render and manage this part in the animation system.
     */
    internal val entity = PartEntity(this)
}
