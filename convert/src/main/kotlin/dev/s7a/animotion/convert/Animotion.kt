package dev.s7a.animotion.convert

import dev.s7a.animotion.convert.data.animotion.AnimotionSettings
import dev.s7a.animotion.convert.data.blockbench.BlockBenchModel
import kotlinx.serialization.json.Json
import java.io.File

data class Animotion(
    val settings: AnimotionSettings,
    val models: List<BlockBenchModel>,
) {
    companion object {
        private val json =
            Json {
                ignoreUnknownKeys = true
            }

        fun load(directory: File): Animotion {
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
