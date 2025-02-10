package dev.s7a.animotion.converter.generator

import dev.s7a.animotion.converter.data.minecraft.item.MinecraftItem
import dev.s7a.animotion.converter.data.minecraft.item.Override
import dev.s7a.animotion.converter.data.minecraft.item.Predicate
import dev.s7a.animotion.converter.loader.ResourcePack
import kotlinx.serialization.json.Json
import java.io.File

class PackGenerator(
    private val resourcePack: ResourcePack,
) {
    fun save(destination: File) {
        var customModelData = 0
        val overrides =
            buildList {
                resourcePack.animotion.models.forEach { model ->
                    model.textures.forEachIndexed { index, texture ->
                        val file = texture.getDestinationFile(destination, resourcePack.animotion.settings.namespace, model.name, index)
                        texture.saveTo(file)
                    }
                    model.toParts(resourcePack.animotion.settings).forEachIndexed { index, part ->
                        part.save(destination, resourcePack.animotion.settings.namespace, model.name, index)
                        customModelData += 1
                        add(Override(Predicate(customModelData), "${resourcePack.animotion.settings.namespace}:${model.name}/$index"))
                    }
                }
            }
        destination.resolve("assets/minecraft/models/item/${resourcePack.animotion.settings.item.minecraft}.json").run {
            parentFile?.mkdirs()
            writeText(
                Json.encodeToString(
                    MinecraftItem(
                        "minecraft:item/generated",
                        mapOf(
                            "layer0" to "minecraft:item/${resourcePack.animotion.settings.item.minecraft}",
                        ),
                        overrides,
                    ),
                ),
            )
        }
    }
}
