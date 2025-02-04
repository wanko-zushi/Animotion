package dev.s7a.animotion.spigot

import com.github.retrooper.packetevents.protocol.entity.data.EntityData
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes
import com.github.retrooper.packetevents.util.Quaternion4f
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import io.github.retrooper.packetevents.util.SpigotReflectionUtil
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID
import kotlin.math.cos
import kotlin.math.sin

internal class AnimotionPartEntity(
    private val animotion: Animotion,
) {
    private val entityId = SpigotReflectionUtil.generateEntityId()
    private val uniqueId = UUID.randomUUID()

    fun spawn(
        location: Location,
        player: Player,
        part: AnimotionPart,
    ): Boolean {
        if (location.world != player.world) return false
        animotion.packetManager.sendPacket(
            player,
            listOf(
                WrapperPlayServerSpawnEntity(
                    entityId,
                    uniqueId,
                    EntityTypes.ITEM_DISPLAY,
                    SpigotConversionUtil.fromBukkitLocation(location.offset(part.position)),
                    0F,
                    0,
                    null,
                ),
                WrapperPlayServerEntityMetadata(
                    entityId,
                    buildList {
                        add(
                            EntityData(
                                Field.ITEM,
                                EntityDataTypes.ITEMSTACK,
                                SpigotConversionUtil.fromBukkitItemStack(
                                    ItemStack(part.material).apply {
                                        itemMeta =
                                            itemMeta?.apply {
                                                setCustomModelData(part.model)
                                            }
                                    },
                                ),
                            ),
                        )

                        if (part.rotation != Rotation.Zero) {
                            add(
                                EntityData(
                                    Field.RIGHT_ROTATION,
                                    EntityDataTypes.QUATERNION,
                                    part.rotation.quaternion(),
                                ),
                            )
                        }
                    },
                ),
            ),
        )
        return true
    }

    private fun Location.offset(position: Position) = Location(world, x + position.x, y + position.y, z + position.z)

    private fun Rotation.quaternion(): Quaternion4f {
        val cx = cos(x / 2)
        val cy = cos(y / 2)
        val cz = cos(z / 2)
        val sx = sin(x / 2)
        val sy = sin(y / 2)
        val sz = sin(z / 2)

        return Quaternion4f(
            (sx * cy * cz - cx * sy * sz).toFloat(),
            (cx * sy * cz + sx * cy * sz).toFloat(),
            (cx * cy * sz - sx * sy * cz).toFloat(),
            (cx * cy * cz + sx * sy * sz).toFloat(),
        )
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
