package dev.s7a.animotion.converter

import dev.s7a.animotion.converter.exception.MinecraftItemNotFoundException
import dev.s7a.animotion.converter.exception.ModelNotFoundException
import dev.s7a.animotion.converter.json.blockbench.BlockBenchModel
import dev.s7a.animotion.converter.json.minecraft.item.MinecraftItem
import dev.s7a.animotion.converter.loader.ResourcePack

class Converter(resourcePack: ResourcePack) {
    val items: Map<Pair<String, MinecraftItem>, List<BlockBenchModel>>

    init {
        val modelByName = resourcePack.animotion.models.associateBy(BlockBenchModel::name)
        items = resourcePack.animotion.settings.items.entries.associate { (itemName, modelNames) ->
            val item = resourcePack.animotion.base[itemName] ?: throw MinecraftItemNotFoundException(itemName)
            val models = modelNames.map { name ->
                modelByName[name] ?: throw ModelNotFoundException(name)
            }
            itemName to item to models
        }
    }
}
