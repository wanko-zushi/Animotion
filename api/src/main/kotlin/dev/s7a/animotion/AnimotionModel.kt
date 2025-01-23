package dev.s7a.animotion

import dev.s7a.animotion.model.Animation
import dev.s7a.animotion.model.Part
import kotlinx.serialization.Serializable

@Serializable
public class AnimotionModel<Material>(
    public val version: Int,
    public val data: AnimotionModelData<Material>,
) {
    public companion object {
        public fun <Material> new(
            parts: List<Part<Material>>,
            animations: Map<String, Animation> = mapOf(),
        ): AnimotionModel<Material> = AnimotionModel(1, AnimotionModelData(parts, animations))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as AnimotionModel<*>

        return data == other.data
    }

    override fun hashCode(): Int = data.hashCode()
}
