package dev.s7a.animotion.spigot

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
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
                fun on(event: PlayerJoinEvent) {
                    val robit = Robit(animotion)
                    val player = event.player
                    val location = player.location
                    robit.spawn(location, player)
                }
            },
            this,
        )
    }

    override fun onDisable() {
        animotion.onDisable()
    }
}
