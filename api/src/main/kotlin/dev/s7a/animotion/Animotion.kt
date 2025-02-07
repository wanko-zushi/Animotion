package dev.s7a.animotion

import dev.s7a.animotion.internal.PacketManager
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
}
