package dev.s7a.animotion.convert.minecraft

import dev.s7a.animotion.convert.data.minecraft.item.MinecraftItem
import dev.s7a.animotion.convert.data.minecraft.item.Override
import dev.s7a.animotion.convert.util.createParentDirectory
import kotlinx.serialization.json.Json
import java.io.File

fun createMinecraftItemFile(
    packDirectory: File,
    item: String,
    overrides: List<Override>,
) {
    val file = MinecraftAsset.ItemModel.resolve(packDirectory, item)
    file.createParentDirectory()
    file.writeText(
        Json.encodeToString(
            MinecraftItem(
                "minecraft:item/generated",
                mapOf(
                    "layer0" to "minecraft:item/$item",
                ),
                overrides,
            ),
        ),
    )
}
