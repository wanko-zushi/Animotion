package dev.s7a.animotion.converter.json.animotion

import dev.s7a.animotion.converter.json.minecraft.model.MinecraftModel
import dev.s7a.animotion.converter.util.path.createParentDirectories
import dev.s7a.animotion.converter.util.path.writeText
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okio.Path

@Serializable
data class Part(val name: String, val model: MinecraftModel) {
    fun save(destination: Path, namespace: String, modelName: String, index: Int) {
        destination.resolve("assets/$namespace/models/$modelName/$index.json").run {
            createParentDirectories()
            writeText(Json.encodeToString(model))
        }
    }
}
