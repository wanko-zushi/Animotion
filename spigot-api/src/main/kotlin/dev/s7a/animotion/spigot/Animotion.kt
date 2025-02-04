package dev.s7a.animotion.spigot

import dev.s7a.animotion.spigot.api.Part
import dev.s7a.animotion.spigot.impl.PacketManager
import dev.s7a.animotion.spigot.impl.PartImpl
import org.bukkit.Material
import org.bukkit.plugin.Plugin

class Animotion(
    plugin: Plugin,
) {
    internal val packetManager = PacketManager(plugin)

    fun onLoad() {
        packetManager.onLoad()
    }

    fun onEnable() {
        packetManager.onEnable()
    }

    fun onDisable() {
        packetManager.onDisable()
    }

    fun createPart(
        material: Material,
        model: Int,
    ): Part = PartImpl(this, material, model)
}
