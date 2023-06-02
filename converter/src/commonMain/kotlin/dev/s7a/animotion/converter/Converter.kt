package dev.s7a.animotion.converter

import dev.s7a.animotion.converter.exception.MinecraftItemNotFoundException
import dev.s7a.animotion.converter.exception.ModelNotFoundException
import dev.s7a.animotion.converter.json.animotion.Part
import dev.s7a.animotion.converter.json.blockbench.BlockBenchModel
import dev.s7a.animotion.converter.json.minecraft.item.MinecraftItem
import dev.s7a.animotion.converter.loader.ResourcePack

class Converter(resourcePack: ResourcePack) {
    val parts: Map<Pair<String, MinecraftItem>, List<Part>>

    init {
        val modelByName = resourcePack.animotion.models.associateBy(BlockBenchModel::name)
        parts = resourcePack.animotion.settings.items.entries.associate { (itemName, modelNames) ->
            val item = resourcePack.animotion.base[itemName] ?: throw MinecraftItemNotFoundException(itemName)
            val models = modelNames.map { name ->
                modelByName[name] ?: throw ModelNotFoundException(name)
            }
            itemName to item to models.flatMap { model ->
                model.toParts(resourcePack.animotion.settings)
            }
        }
    }
}
