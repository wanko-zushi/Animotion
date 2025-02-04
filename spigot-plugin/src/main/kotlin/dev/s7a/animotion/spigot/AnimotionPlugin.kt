package dev.s7a.animotion.spigot

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class AnimotionPlugin : JavaPlugin() {
    private val animotion = Animotion(this)

    override fun onLoad() {
        animotion.onLoad()
    }

    override fun onEnable() {
        animotion.onEnable()
        server.pluginManager.registerEvents(
            object : Listener {
                @EventHandler
                fun on(event: AsyncPlayerChatEvent) {
                    val part = animotion.createPart(Material.KELP, 2)
                    val player = event.player
                    val location = player.location
                    part.spawn(location, player)
                }
            },
            this,
        )
    }

    override fun onDisable() {
        animotion.onDisable()
    }
}
