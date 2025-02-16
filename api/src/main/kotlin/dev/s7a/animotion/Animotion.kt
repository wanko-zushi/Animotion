package dev.s7a.animotion

import dev.s7a.animotion.internal.PacketManager
import org.bukkit.Material
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask

class Animotion(
    private val plugin: Plugin,
    val material: Material = Material.STICK,
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

    fun runTaskLaterAsync(
        delay: Long,
        task: () -> Unit,
    ): BukkitTask = plugin.server.scheduler.runTaskLaterAsynchronously(plugin, task, delay)
}
