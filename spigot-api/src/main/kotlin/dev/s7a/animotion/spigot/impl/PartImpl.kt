package dev.s7a.animotion.spigot.impl

import com.github.retrooper.packetevents.protocol.entity.data.EntityData
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity
import dev.s7a.animotion.spigot.Animotion
import dev.s7a.animotion.spigot.api.Part
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import io.github.retrooper.packetevents.util.SpigotReflectionUtil
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

internal class PartImpl(
    private val animotion: Animotion,
    private val material: Material,
    private val model: Int,
) : Part {
    private val entityId = SpigotReflectionUtil.generateEntityId()
    private val uniqueId = UUID.randomUUID()

    override fun spawn(
        location: Location,
        player: Player,
    ): Boolean {
        if (location.world != player.world) return false
        animotion.packetManager.sendPacket(
            player,
            listOf(
                WrapperPlayServerSpawnEntity(
                    entityId,
                    uniqueId,
                    EntityTypes.ITEM_DISPLAY,
                    SpigotConversionUtil.fromBukkitLocation(location),
                    location.yaw,
                    0,
                    null,
                ),
                WrapperPlayServerEntityMetadata(
                    entityId,
                    listOf(
                        EntityData(
                            23,
                            EntityDataTypes.ITEMSTACK,
                            SpigotConversionUtil.fromBukkitItemStack(
                                ItemStack(material).apply {
                                    itemMeta =
                                        itemMeta?.apply {
                                            setCustomModelData(model)
                                        }
                                },
                            ),
                        ),
                    ),
                ),
            ),
        )
        return true
    }

    private object Field {
        // https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Entity_metadata#Display
        const val TRANSLATION = 11
        const val SCALE = 12
        const val LEFT_ROTATION = 13
        const val RIGHT_ROTATION = 14

        // https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Entity_metadata#Item_Display
        const val ITEM = 23
    }
}
