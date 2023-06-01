package dev.s7a.animotion.converter.loader

import dev.s7a.animotion.converter.json.minecraft.PackMeta
import kotlinx.serialization.json.Json
import java.io.File

data class ResourcePack(val meta: PackMeta, val animotion: Animotion) {
    companion object {
        fun load(directory: File, json: Json = Json.Default): ResourcePack {
            val meta = json.decodeFromString<PackMeta>(directory.resolve("pack.mcmeta").readText())
            val animotion = Animotion.load(directory.resolve("animotion"), json)
            return ResourcePack(meta, animotion)
        }
    }
}
