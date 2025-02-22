package dev.s7a.animotion.convert

import dev.s7a.animotion.convert.blockbench.isBlockbenchModel
import dev.s7a.animotion.convert.data.AnimotionSettings
import dev.s7a.animotion.convert.data.BlockbenchModel
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

data class Animotion(
    val settings: AnimotionSettings,
    val models: List<BlockbenchModel>,
) {
    private val customModelData = AtomicInteger()

    val parts =
        models.associateWith { model ->
            Part.from(model, customModelData)
        }

    companion object {
        fun load(directory: File): Animotion {
            val settings = AnimotionSettings.loadOrDefault(directory.resolve("settings.json"))
            val models =
                directory
                    .listFiles()
                    .orEmpty()
                    .filter(File::isBlockbenchModel)
                    .map(BlockbenchModel::load)
            return Animotion(settings, models)
        }
    }
}
