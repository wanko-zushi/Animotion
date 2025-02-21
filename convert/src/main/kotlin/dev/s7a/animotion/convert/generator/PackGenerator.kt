package dev.s7a.animotion.convert.generator

import dev.s7a.animotion.convert.InputPack
import dev.s7a.animotion.convert.createParts
import dev.s7a.animotion.convert.data.minecraft.item.MinecraftItem
import dev.s7a.animotion.convert.data.minecraft.item.Override
import dev.s7a.animotion.convert.data.minecraft.item.Predicate
import kotlinx.serialization.json.Json
import java.io.File

class PackGenerator(
    private val resourcePack: InputPack,
) {
    fun save(destination: File) {
        val overrides =
            buildList {
                resourcePack.animotion.models.createParts(resourcePack.animotion.settings.namespace).forEach { (model, parts) ->
                    model.textures.forEachIndexed { index, texture ->
                        val file = texture.getDestinationFile(destination, resourcePack.animotion.settings.namespace, model.name, index)
                        texture.saveTo(file)
                    }
                    parts.forEachIndexed { index, part ->
                        part.save(destination, resourcePack.animotion.settings.namespace, model.name, index)
                        add(Override(Predicate(part.customModelData), "${resourcePack.animotion.settings.namespace}:${model.name}/$index"))
                    }
                }
            }
        destination.resolve("assets/minecraft/models/item/${resourcePack.animotion.settings.item}.json").run {
            parentFile?.mkdirs()
            writeText(
                Json.encodeToString(
                    MinecraftItem(
                        "minecraft:item/generated",
                        mapOf(
                            "layer0" to "minecraft:item/${resourcePack.animotion.settings.item}",
                        ),
                        overrides,
                    ),
                ),
            )
        }
    }
}
