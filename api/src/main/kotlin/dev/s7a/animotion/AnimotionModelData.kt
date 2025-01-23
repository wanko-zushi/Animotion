package dev.s7a.animotion

import dev.s7a.animotion.model.Animation
import dev.s7a.animotion.model.Part
import kotlinx.serialization.Serializable

@Serializable
public data class AnimotionModelData<Material>(
    public val parts: List<Part<Material>>,
    public val animations: Map<String, Animation> = mapOf(),
)
