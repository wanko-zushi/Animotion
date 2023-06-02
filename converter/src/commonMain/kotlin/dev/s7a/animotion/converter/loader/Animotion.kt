package dev.s7a.animotion.converter.loader

import dev.s7a.animotion.converter.json.animotion.AnimotionSettings
import dev.s7a.animotion.converter.json.blockbench.BlockBenchModel
import dev.s7a.animotion.converter.json.minecraft.MinecraftModel
import dev.s7a.animotion.converter.util.path.extension
import dev.s7a.animotion.converter.util.path.list
import dev.s7a.animotion.converter.util.path.nameWithoutExtension
import dev.s7a.animotion.converter.util.path.readText
import kotlinx.serialization.json.Json
import okio.Path

data class Animotion(val settings: AnimotionSettings, val models: Map<String, BlockBenchModel>, val base: Map<String, MinecraftModel>) {
    companion object {
        fun load(directory: Path, json: Json = Json.Default): Animotion {
            val settings = json.decodeFromString<AnimotionSettings>(directory.resolve("settings.json").readText())
            val models = directory.list().filter { it.extension == "bbmodel" }.associate {
                it.nameWithoutExtension to json.decodeFromString<BlockBenchModel>(it.readText())
            }
            val base = directory.resolve("base").list().filter { it.extension == "json" }.associate {
                it.name to json.decodeFromString<MinecraftModel>(it.readText())
            }
            return Animotion(settings, models, base)
        }
    }
}
