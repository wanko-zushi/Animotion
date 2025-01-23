package dev.s7a.animotion.converter.json.animotion

import dev.s7a.animotion.converter.json.minecraft.model.MinecraftModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Part(
    val name: String,
    val model: MinecraftModel,
) {
    fun save(
        destination: File,
        namespace: String,
        modelName: String,
        index: Int,
    ) {
        destination.resolve("assets/$namespace/models/$modelName/$index.json").run {
            parentFile?.mkdirs()
            writeText(Json.encodeToString(model))
        }
    }
}
