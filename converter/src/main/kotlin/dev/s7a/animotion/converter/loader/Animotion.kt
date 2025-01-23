package dev.s7a.animotion.converter.loader

import dev.s7a.animotion.converter.json.animotion.AnimotionSettings
import dev.s7a.animotion.converter.json.blockbench.BlockBenchModel
import dev.s7a.animotion.converter.json.minecraft.item.MinecraftItem
import kotlinx.serialization.json.Json
import java.io.File

data class Animotion(val settings: AnimotionSettings, val models: List<BlockBenchModel>, val base: Map<String, MinecraftItem>) {
    companion object {
        fun load(directory: File, json: Json = Json.Default): Animotion {
            val settings = json.decodeFromString<AnimotionSettings>(directory.resolve("settings.json").readText())
            val models = directory.listFiles().orEmpty().filter { it.extension == "bbmodel" }.map {
                json.decodeFromString<BlockBenchModel>(it.readText())
            }
            val base = directory.resolve("base").listFiles().orEmpty().filter { it.extension == "json" }.associate {
                it.nameWithoutExtension to json.decodeFromString<MinecraftItem>(it.readText())
            }
            return Animotion(settings, models, base)
        }
    }
}
