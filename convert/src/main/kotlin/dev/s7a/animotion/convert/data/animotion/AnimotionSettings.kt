package dev.s7a.animotion.convert.data.animotion

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimotionSettings(
    val namespace: String = "animotion",
    val item: String = "stick",
    @SerialName("package")
    val packageName: String = "",
)
