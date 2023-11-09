package dev.s7a.animotion.converter.loader

import dev.s7a.animotion.converter.exception.UnsupportedPackFormatException
import dev.s7a.animotion.converter.json.minecraft.PackMeta
import dev.s7a.animotion.converter.util.path.readText
import kotlinx.serialization.json.Json
import okio.Path

data class ResourcePack(val meta: PackMeta, val animotion: Animotion) {
    companion object {
        fun load(directory: Path, json: Json = Json.Default, onErrorAction: (Exception) -> Unit = { exception -> throw exception }): ResourcePack {
            val meta = json.decodeFromString<PackMeta>(directory.resolve("pack.mcmeta").readText())
            if (meta.pack.packFormat < 13) {
                onErrorAction(UnsupportedPackFormatException(meta.pack.packFormat, "< 13"))
            }
            val animotion = Animotion.load(directory.resolve("animotion"), json)
            return ResourcePack(meta, animotion)
        }
    }
}
