package dev.s7a.animotion.converter

import dev.s7a.animotion.converter.exception.MinecraftItemNotFoundException
import dev.s7a.animotion.converter.exception.ModelNotFoundException
import dev.s7a.animotion.converter.json.animotion.Part
import dev.s7a.animotion.converter.json.blockbench.BlockBenchModel
import dev.s7a.animotion.converter.json.minecraft.item.MinecraftItem
import dev.s7a.animotion.converter.json.minecraft.item.Override
import dev.s7a.animotion.converter.json.minecraft.item.Predicate
import dev.s7a.animotion.converter.loader.ResourcePack
import dev.s7a.animotion.converter.util.path.createParentDirectories
import dev.s7a.animotion.converter.util.path.writeText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okio.Path

class Converter(private val resourcePack: ResourcePack) {
    val parts: Map<Pair<String, MinecraftItem>, List<Pair<BlockBenchModel, List<Part>>>>

    init {
        val modelByName = resourcePack.animotion.models.associateBy(BlockBenchModel::name)
        parts = resourcePack.animotion.settings.items.entries.associate { (itemName, modelNames) ->
            val item = resourcePack.animotion.base[itemName] ?: throw MinecraftItemNotFoundException(itemName)
            val models = modelNames.map { name ->
                modelByName[name] ?: throw ModelNotFoundException(name)
            }
            itemName to item to models.map { model ->
                model to model.toParts(resourcePack.animotion.settings)
            }
        }
    }

    fun save(destination: Path) {
        val namespace = resourcePack.animotion.settings.namespace
        parts.forEach { (key, value) ->
            val (itemName, item) = key
            var customModelData = 0
            val overrides = value.flatMap { (bbModel, parts) ->
                bbModel.textures.forEachIndexed { index, texture ->
                    texture.save(destination, namespace, bbModel.name, index)
                }
                parts.mapIndexed { index, part ->
                    customModelData += 1
                    part.save(destination, namespace, bbModel.name, index)
                    Override(Predicate(customModelData), "$namespace:${bbModel.name}/$index")
                }
            }
            destination.resolve("assets/minecraft/models/item/$itemName.json").run {
                createParentDirectories()
                writeText(Json.encodeToString(item.copy(overrides = overrides)))
            }
        }
    }
}
