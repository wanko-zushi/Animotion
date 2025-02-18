package dev.s7a.animotion.convert.data.blockbench

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
    fun getDestinationFile(
        directory: File,
        namespace: String,
        modelName: String,
        index: Int,
    ) = directory.resolve("assets/$namespace/textures/item/$modelName/$index.png")

    @OptIn(ExperimentalEncodingApi::class)
    fun saveTo(file: File) {
        require(source.startsWith("data:image/png;base64,"))
        file.run {
            parentFile?.mkdirs()
            writeBytes(Base64.decode(source.drop(22)))
        }
    }
}
