package dev.s7a.animotion.converter.loader

import dev.s7a.animotion.converter.data.animotion.AnimotionSettings
import dev.s7a.animotion.converter.data.blockbench.BlockBenchModel
import kotlinx.serialization.json.Json
import java.io.File

data class Animotion(
    val settings: AnimotionSettings,
    val models: List<BlockBenchModel>,
) {
    companion object {
        fun load(
            directory: File,
            json: Json = Json.Default,
        ): Animotion {
            val settingsFile = directory.resolve("settings.json")
            val settings =
                if (settingsFile.exists()) {
                    json.decodeFromString<AnimotionSettings>(settingsFile.readText())
                } else {
                    AnimotionSettings()
                }
            val models =
                directory.listFiles().orEmpty().filter { it.extension == "bbmodel" }.map {
                    json.decodeFromString<BlockBenchModel>(it.readText())
                }
            return Animotion(settings, models)
        }
    }
}
