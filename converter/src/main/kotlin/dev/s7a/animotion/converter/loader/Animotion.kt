package dev.s7a.animotion.converter.loader

import dev.s7a.animotion.converter.data.animotion.AnimotionSettings
import dev.s7a.animotion.converter.data.animotion.Part
import dev.s7a.animotion.converter.data.blockbench.BlockBenchModel
import kotlinx.serialization.json.Json
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

data class Animotion(
    val settings: AnimotionSettings,
    val models: Map<BlockBenchModel, List<Part>>,
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
