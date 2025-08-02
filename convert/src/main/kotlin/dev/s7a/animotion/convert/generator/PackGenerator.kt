package dev.s7a.animotion.convert.generator

import dev.s7a.animotion.convert.Animotion
import dev.s7a.animotion.convert.data.MinecraftItem
import dev.s7a.animotion.convert.data.MinecraftItemModel
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

        // Generate texture
        animotion.parts.keys.forEach { model ->
            model.textures.forEachIndexed { index, texture ->
                val file = MinecraftAsset.ItemTexture.resolve(packDirectory, "${model.name}/$index", namespace)
                val image = texture.toImage()
                file.createParentDirectory()
                file.writeBytes(image)
            }
        }

        // Generate model
        animotion.parts.forEach { (model, parts) ->
            parts.forEachIndexed { index, part ->
                val minecraftModel = part.toMinecraftModel(namespace)
                val file = MinecraftAsset.Model.resolve(packDirectory, "${model.name}/$index", namespace)
                file.createParentDirectory()
                file.writeText(Json.encodeToString(minecraftModel))
            }
        }

        // Generate item
        val item = animotion.settings.item
        if (item != null) {
            createMinecraftItemFile(
                packDirectory,
                item.material,
                animotion.parts.flatMap { (model, parts) ->
                    parts.mapIndexed { index, part ->
                        MinecraftItem.Override(
                            MinecraftItem.Override.Predicate(part.customModelData),
                            "$namespace:${model.name}/$index",
                        )
                    }
                },
            )
        }

        // Generate item model
        if (animotion.settings.itemModel) {
            animotion.parts.forEach { (model, parts) ->
                parts.forEachIndexed { index, part ->
                    val file = MinecraftAsset.ItemModel.resolve(packDirectory, "${model.name}/$index", namespace)
                    file.createParentDirectory()
                    file.writeText(
                        Json.encodeToString(
                            MinecraftItemModel(
                                MinecraftItemModel.Model(
                                    "minecraft:model",
                                    "$namespace:${model.name}/$index",
                                ),
                            ),
                        ),
                    )
                }
            }
        }
    }
}
