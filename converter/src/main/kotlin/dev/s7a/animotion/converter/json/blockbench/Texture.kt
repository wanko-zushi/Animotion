package dev.s7a.animotion.converter.json.blockbench

import kotlinx.serialization.Serializable
import java.io.File
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Serializable
data class Texture(
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
