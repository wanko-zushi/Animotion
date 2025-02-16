package dev.s7a.animotion.convert.loader

import dev.s7a.animotion.convert.data.animotion.AnimotionSettings
import dev.s7a.animotion.convert.data.animotion.Part
import dev.s7a.animotion.convert.data.blockbench.BlockBenchModel
import kotlinx.serialization.json.Json
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

data class Animotion(
    val settings: AnimotionSettings,
    val models: Map<BlockBenchModel, List<Part>>,
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
            val customModelData = AtomicInteger()
            val models =
                directory.listFiles().orEmpty().filter { it.extension == "bbmodel" }.associate {
                    val model = json.decodeFromString<BlockBenchModel>(it.readText())
                    model to Part.from(model, settings.namespace, customModelData)
                }
            return Animotion(settings, models)
        }
    }
}
