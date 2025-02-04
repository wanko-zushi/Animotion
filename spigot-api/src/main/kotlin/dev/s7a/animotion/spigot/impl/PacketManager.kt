package dev.s7a.animotion.spigot.impl

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.wrapper.PacketWrapper
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

internal class PacketManager(
    private val plugin: Plugin,
) {
    fun onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(plugin))
        PacketEvents.getAPI().load()
    }

    fun onEnable() {
        PacketEvents.getAPI().init()
    }

    fun onDisable() {
        PacketEvents.getAPI().terminate()
    }

    fun sendPacket(
        player: Player,
        packet: PacketWrapper<*>,
    ) {
        PacketEvents.getAPI().playerManager.sendPacket(player, packet)
    }

    fun sendPacket(
        player: Player,
        packets: Iterable<PacketWrapper<*>>,
    ) {
        packets.forEach { packet ->
            sendPacket(player, packet)
        }
    }
}
