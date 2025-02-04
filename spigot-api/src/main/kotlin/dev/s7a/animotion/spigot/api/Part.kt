package dev.s7a.animotion.spigot.api

import org.bukkit.Location
import org.bukkit.entity.Player

interface Part {
    fun spawn(
        location: Location,
        player: Player,
    ): Boolean
}
