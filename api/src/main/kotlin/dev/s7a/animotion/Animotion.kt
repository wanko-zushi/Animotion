package dev.s7a.animotion

import dev.s7a.animotion.internal.PacketManager
import org.bukkit.Material
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask

/**
 * A utility class for handling animations and packet management in Bukkit plugins.
 *
 * This class streamlines packet handling and animation tasks, providing an intuitive way to integrate
 * animations into plugins. Essential lifecycle management methods (`onLoad`, `onEnable`, `onDisable`)
 * simplify plugin setup and teardown.
 *
 * @param plugin The Bukkit plugin instance responsible for managing this utility.
 * @param material The default material for animations, set to STICK by default.
 */
class Animotion(
    private val plugin: Plugin,
    val material: Material = Material.STICK,
) {
    internal val packetManager = PacketManager(plugin)

    /**
     * Invoked during the plugin's load phase to initialize critical components.
     *
     * This method prepares the packet management system, ensuring that subsequent
     * runtime plugin operations function correctly.
     */
    fun onLoad() {
        packetManager.onLoad()
    }

    /**
     * Invoked when the plugin is enabled, preparing resources for runtime operations.
     *
     * During this phase, the system ensures all animations and packets are set up
     * and ready for handling user interactions.
     */
    fun onEnable() {
        packetManager.onEnable()
    }

    /**
     * Invoked when the plugin is disabled to perform resource cleanup and state preservation.
     *
     * This method ensures resources are properly released and any animation or
     * packet-related states are saved or safely discarded.
     */
    fun onDisable() {
        packetManager.onDisable()
    }

    /**
     * Schedules a task to run asynchronously after a specified delay.
     *
     * This method is useful for deferring non-blocking operations without
     * interfering with the server's main thread, allowing plugins to perform
     * background tasks effectively.
     *
     * @param delay The delay in server ticks before executing the task.
     * @param task The task to be executed asynchronously.
     * @return The scheduled BukkitTask instance for managing the task.
     */
    fun runTaskLaterAsync(
        delay: Long,
        task: () -> Unit,
    ): BukkitTask = plugin.server.scheduler.runTaskLaterAsynchronously(plugin, task, delay)
}
