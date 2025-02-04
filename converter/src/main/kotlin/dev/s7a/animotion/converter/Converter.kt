package dev.s7a.animotion.converter

import dev.s7a.animotion.converter.exception.MinecraftItemNotFoundException
import dev.s7a.animotion.converter.exception.ModelNotFoundException
import dev.s7a.animotion.converter.json.animotion.Part
import dev.s7a.animotion.converter.json.blockbench.BlockBenchModel
import dev.s7a.animotion.converter.json.minecraft.item.MinecraftItem
import dev.s7a.animotion.converter.json.minecraft.item.Override
import dev.s7a.animotion.converter.json.minecraft.item.Predicate
import dev.s7a.animotion.converter.loader.ResourcePack
import kotlinx.serialization.json.Json
import java.io.File

class Converter(
    private val resourcePack: ResourcePack,
) {
    val items: Map<Pair<String, MinecraftItem>, List<Pair<BlockBenchModel, List<Part>>>>

    init {
        val modelByName = resourcePack.animotion.models.associateBy(BlockBenchModel::name)
        items =
            resourcePack.animotion.settings.items.entries.associate { (itemName, modelNames) ->
                val item = resourcePack.animotion.base[itemName] ?: throw MinecraftItemNotFoundException(itemName)
                val models =
                    modelNames.map { name ->
                        modelByName[name] ?: throw ModelNotFoundException(name)
                    }
                itemName to item to
                    models.map { model ->
                        model to model.toParts(resourcePack.animotion.settings)
                    }
            }
    }

    fun save(destination: File) {
        items.forEach { (key, value) ->
            val (itemName, item) = key
            var customModelData = 0
            val overrides =
                value.flatMap { (model, parts) ->
                    model.textures.forEachIndexed { index, texture ->
                        val file = texture.getDestinationFile(destination, resourcePack.animotion.settings.namespace, model.name, index)
                        texture.saveTo(file)
                    }
                    parts.mapIndexed { index, part ->
                        customModelData += 1
                        part.save(destination, resourcePack.animotion.settings.namespace, model.name, index)
                        Override(Predicate(customModelData), "${resourcePack.animotion.settings.namespace}:${model.name}/$index")
                    }
                }
            destination.resolve("assets/minecraft/models/item/$itemName.json").run {
                parentFile?.mkdirs()
                writeText(Json.encodeToString(item.copy(overrides = overrides)))
            }
        }
    }
}
