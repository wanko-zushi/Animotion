package dev.s7a.animotion.convert

import dev.s7a.animotion.convert.blockbench.isBlockbenchModel
import dev.s7a.animotion.convert.data.animotion.AnimotionSettings
import dev.s7a.animotion.convert.data.blockbench.BlockbenchModel
import java.io.File

data class Animotion(
    val settings: AnimotionSettings,
    val models: List<BlockbenchModel>,
) {
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
