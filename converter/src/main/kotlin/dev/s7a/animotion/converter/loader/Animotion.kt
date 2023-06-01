package dev.s7a.animotion.converter.loader

import dev.s7a.animotion.converter.json.animotion.AnimotionSettings
import dev.s7a.animotion.converter.json.blockbench.BlockBenchModel
import dev.s7a.animotion.converter.json.minecraft.MinecraftModel
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileFilter

data class Animotion(val settings: AnimotionSettings, val models: Map<String, BlockBenchModel>, val base: Map<String, MinecraftModel>) {
    companion object {
        fun load(directory: File, json: Json = Json.Default): Animotion {
            val settings = json.decodeFromString<AnimotionSettings>(directory.resolve("settings.json").readText())
            val models = directory.listFiles(FileFilter { it.extension == "bbmodel" }).associate {
                it.nameWithoutExtension to json.decodeFromString<BlockBenchModel>(it.readText())
            }
            val base = directory.resolve("base").listFiles(FileFilter { it.extension == "json" }).associate {
                it.name to json.decodeFromString<MinecraftModel>(it.readText())
            }
            return Animotion(settings, models, base)
        }
    }
}
