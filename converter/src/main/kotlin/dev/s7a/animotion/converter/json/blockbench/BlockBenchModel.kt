package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockBenchModel(
    var meta: Meta? = Meta(),
    val name: String? = null,
    @SerialName("model_identifier") val modelIdentifier: String? = null,
    @SerialName("visible_box") val visibleBox: List<Int> = listOf(),
    @SerialName("variable_placeholders") val variablePlaceholders: String? = null,
    @SerialName("variable_placeholder_buttons") val variablePlaceholderButtons: List<String> = listOf(),
    @SerialName("timeline_setups") val timelineSetups: List<String> = listOf(),
    val resolution: Resolution? = Resolution(),
    val elements: List<Elements> = listOf(),
    val outliner: List<Outliner> = listOf(),
    val textures: List<Textures> = listOf(),
    val animations: List<Animations> = listOf(),
)
