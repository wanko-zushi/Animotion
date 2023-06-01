package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Textures(
    val path: String? = null,
    val name: String? = null,
    val folder: String? = null,
    val namespace: String? = null,
    val id: String? = null,
    val particle: Boolean? = null,
    @SerialName("render_mode") val renderMode: String? = null,
    @SerialName("render_sides") val renderSides: String? = null,
    @SerialName("frame_time") val frameTime: Int? = null,
    @SerialName("frame_order_type") val frameOrderType: String? = null,
    @SerialName("frame_order") val frameOrder: String? = null,
    @SerialName("frame_interpolate") val frameInterpolate: Boolean? = null,
    val visible: Boolean? = null,
    val mode: String? = null,
    val saved: Boolean? = null,
    val uuid: String? = null,
    @SerialName("relative_path") val relativePath: String? = null,
    val source: String? = null,
)
