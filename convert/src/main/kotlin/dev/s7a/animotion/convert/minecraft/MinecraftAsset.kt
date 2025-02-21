package dev.s7a.animotion.convert.minecraft

import java.io.File

enum class MinecraftAsset(
    private val path: String,
    private val extension: String,
) {
    Model("models", "json"),
    Texture("textures", "png"),
    ItemModel(Model, "item"),
    ;

    constructor(parent: MinecraftAsset, path: String) : this("${parent.path}/$path", parent.extension)

    fun resolve(
        packDirectory: File,
        name: String,
        namespace: String = "minecraft",
    ) = packDirectory.resolve(get(name, namespace))

    fun get(
        name: String,
        namespace: String = "minecraft",
    ) = "assets/$namespace/$path/$name.$extension"
}
