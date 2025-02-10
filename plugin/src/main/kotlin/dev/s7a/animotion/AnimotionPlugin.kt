package dev.s7a.animotion

import dev.s7a.animotion.generated.Robit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
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
                val robit = Robit(animotion)

                @EventHandler
                fun on(event: PlayerJoinEvent) {
                    val player = event.player
                    val location = player.location
                    robit.spawn(player, location)
                }

                @EventHandler
                fun on(event: AsyncPlayerChatEvent) {
                    val player = event.player
                    val message = event.message
                    when (message) {
                        "reset" -> robit.reset(player)
                        "standing" -> robit.standing.play(player)
                        "walking" -> robit.walking.play(player)
                        "question" -> robit.question.play(player)
                        "freeze" -> robit.freeze.play(player)
                    }
                }
            },
            this,
        )
    }

    override fun onDisable() {
        animotion.onDisable()
    }
}
