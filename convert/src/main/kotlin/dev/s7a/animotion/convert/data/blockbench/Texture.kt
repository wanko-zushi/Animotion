package dev.s7a.animotion.convert.data.blockbench

import dev.s7a.animotion.convert.util.createParentDirectory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Serializable
data class Texture(
    @SerialName("uv_width")
    val uvWidth: Double = 64.0,
    @SerialName("uv_height")
    val uvHeight: Double = 64.0,
    val source: String,
) {
    @OptIn(ExperimentalEncodingApi::class)
    fun saveTo(file: File) {
        require(source.startsWith("data:image/png;base64,"))
        file.createParentDirectory()
        file.writeBytes(Base64.decode(source.drop(22)))
    }
}
