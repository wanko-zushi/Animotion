package dev.s7a.animotion

import com.github.retrooper.packetevents.protocol.entity.data.EntityData
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes
import com.github.retrooper.packetevents.protocol.item.ItemStack
import com.github.retrooper.packetevents.protocol.nbt.NBTInt
import com.github.retrooper.packetevents.protocol.nbt.NBTString
import com.github.retrooper.packetevents.util.Quaternion4f
import com.github.retrooper.packetevents.util.Vector3f
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import io.github.retrooper.packetevents.util.SpigotReflectionUtil
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.util.Vector
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
                                part.model.itemStack(),
                            ),
                        )

                        if (part.rotation.isZero.not()) {
                            add(
                                EntityData(
                                    Field.LEFT_ROTATION,
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

    fun transform(
        player: Player,
        transformation: AnimotionTransformation,
    ) {
        animotion.packetManager.sendPacket(
            player,
            buildList {
                if (transformation.translation != null) {
                    add(
                        WrapperPlayServerEntityMetadata(
                            entityId,
                            listOf(
                                EntityData(Field.TRANSLATION, EntityDataTypes.VECTOR3F, transformation.translation.vector3f()),
                            ),
                        ),
                    )
                }
                if (transformation.scale != null) {
                    add(
                        WrapperPlayServerEntityMetadata(
                            entityId,
                            listOf(
                                EntityData(Field.SCALE, EntityDataTypes.VECTOR3F, transformation.scale.vector3f()),
                            ),
                        ),
                    )
                }
                if (transformation.rotation != null) {
                    add(
                        WrapperPlayServerEntityMetadata(
                            entityId,
                            listOf(
                                EntityData(Field.LEFT_ROTATION, EntityDataTypes.QUATERNION, transformation.rotation.quaternion()),
                            ),
                        ),
                    )
                }
            },
        )
    }

    private fun AnimotionPart.Model.itemStack() =
        when (this) {
            is AnimotionPart.Model.ItemModel -> {
                ItemStack
                    .builder()
                    .type(
                        SpigotConversionUtil.fromBukkitItemMaterial(Material.STICK),
                    ).nbt("item_model", NBTString(itemModel))
                    .build()
            }
            is AnimotionPart.Model.CustomModelData -> {
                ItemStack
                    .builder()
                    .type(
                        SpigotConversionUtil.fromBukkitItemMaterial(material),
                    ).nbt("CustomModelData", NBTInt(customModelData))
                    .build()
            }
            is AnimotionPart.Model.Both -> {
                ItemStack
                    .builder()
                    .type(
                        SpigotConversionUtil.fromBukkitItemMaterial(material),
                    ).nbt("item_model", NBTString(itemModel))
                    .nbt("CustomModelData", NBTInt(customModelData))
                    .build()
            }
        }

    private fun Location.offset(position: Vector) = Location(world, x + position.x, y + position.y, z + position.z)

    private fun Vector.vector3f() = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())

    private fun Vector.quaternion(): Quaternion4f {
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

        // https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Entity_metadata#Item_Display
        const val ITEM = 23
    }
}
