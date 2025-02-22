package dev.s7a.animotion.convert.generator

import dev.s7a.animotion.convert.Animotion
import dev.s7a.animotion.convert.createParts
import dev.s7a.animotion.convert.data.MinecraftItem
import dev.s7a.animotion.convert.minecraft.MinecraftAsset
import dev.s7a.animotion.convert.minecraft.createMinecraftItemFile
import dev.s7a.animotion.convert.util.createParentDirectory
import kotlinx.serialization.json.Json
import java.io.File

class PackGenerator(
    private val animotion: Animotion,
) {
    fun save(packDirectory: File) {
        val namespace = animotion.settings.namespace
        val overrides =
            buildList {
                animotion.models.createParts().forEach { (model, parts) ->
                    model.textures.forEachIndexed { index, texture ->
                        val file = MinecraftAsset.Texture.resolve(packDirectory, "${model.name}/$index", namespace)
                        val image = texture.toImage()
                        file.createParentDirectory()
                        file.writeBytes(image)
                    }
                    parts.forEachIndexed { index, part ->
                        val minecraftModel = part.toMinecraftModel(namespace)
                        val file = MinecraftAsset.Model.resolve(packDirectory, "${model.name}/$index", namespace)
                        file.createParentDirectory()
                        file.writeText(Json.encodeToString(minecraftModel))
                        add(
                            MinecraftItem.Override(
                                MinecraftItem.Override.Predicate(part.customModelData),
                                "$namespace:${model.name}/$index",
                            ),
                        )
                    }
                }
            }
        createMinecraftItemFile(packDirectory, animotion.settings.item, overrides)
    }
}
