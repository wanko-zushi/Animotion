package dev.s7a.animotion.converter.json.blockbench

import dev.s7a.animotion.converter.util.path.createParentDirectories
import dev.s7a.animotion.converter.util.path.saveImage
import kotlinx.serialization.Serializable
import okio.Path

@Serializable
data class Texture(val source: String) {
    fun save(destination: Path, namespace: String, modelName: String, index: Int) {
        destination.resolve("assets/$namespace/textures/item/$modelName/$index.png").run {
            createParentDirectories()
            saveImage(source)
        }
    }
}
