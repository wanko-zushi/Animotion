package dev.s7a.animotion.convert.generator

import dev.s7a.animotion.convert.InputPack
import dev.s7a.animotion.convert.createParts
import dev.s7a.animotion.convert.data.minecraft.item.Override
import dev.s7a.animotion.convert.data.minecraft.item.Predicate
import dev.s7a.animotion.convert.minecraft.MinecraftAsset
import dev.s7a.animotion.convert.minecraft.createMinecraftItemFile
import java.io.File

class PackGenerator(
    private val resourcePack: InputPack,
) {
    fun save(packDirectory: File) {
        val namespace = resourcePack.animotion.settings.namespace
        val overrides =
            buildList {
                resourcePack.animotion.models.createParts(namespace).forEach { (model, parts) ->
                    model.textures.forEachIndexed { index, texture ->
                        val file = MinecraftAsset.Texture.resolve(packDirectory, "${model.name}/$index", namespace)
                        texture.saveTo(file)
                    }
                    parts.forEachIndexed { index, part ->
                        part.save(packDirectory, namespace, model.name, index)
                        add(Override(Predicate(part.customModelData), "$namespace:${model.name}/$index"))
                    }
                }
            }
        createMinecraftItemFile(packDirectory, resourcePack.animotion.settings.item, overrides)
    }
}
