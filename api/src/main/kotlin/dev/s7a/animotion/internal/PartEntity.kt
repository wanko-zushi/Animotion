package dev.s7a.animotion.internal

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
import dev.s7a.animotion.Animotion
import dev.s7a.animotion.data.Keyframe
import dev.s7a.animotion.data.Part
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import io.github.retrooper.packetevents.util.SpigotReflectionUtil
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.UUID
import kotlin.math.cos
import kotlin.math.sin

internal class PartEntity(
    private val animotion: Animotion,
) {
    private val entityId = SpigotReflectionUtil.generateEntityId()
    private val uniqueId = UUID.randomUUID()

    fun spawn(
        player: Player,
        location: Location,
        part: Part,
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
                                    part.rotation.radians().quaternion(),
                                ),
                            )
                        }
                    },
                ),
            ),
        )
        return true
    }

    fun resetTransform(
        player: Player,
        part: Part,
    ) {
        animotion.packetManager.sendPacket(
            player,
            WrapperPlayServerEntityMetadata(
                entityId,
                listOf(
                    EntityData(
                        Field.TRANSLATION,
                        EntityDataTypes.VECTOR3F,
                        Vector3f.zero(),
                    ),
                    EntityData(
                        Field.SCALE,
                        EntityDataTypes.VECTOR3F,
                        Vector3f(1f, 1f, 1f),
                    ),
                    EntityData(
                        Field.LEFT_ROTATION,
                        EntityDataTypes.QUATERNION,
                        part.rotation.radians().quaternion(),
                    ),
                ),
            ),
        )
    }

    fun transform(
        player: Player,
        part: Part,
        keyframe: Keyframe,
        interpolationDuration: Int?,
    ) {
        animotion.packetManager.sendPacket(
            player,
            WrapperPlayServerEntityMetadata(
                entityId,
                buildList {
                    when (keyframe.channel) {
                        Keyframe.Channel.Position -> {
                            add(
                                EntityData(
                                    Field.TRANSLATION,
                                    EntityDataTypes.VECTOR3F,
                                    keyframe.value.vector3f(),
                                ),
                            )
                        }
                        Keyframe.Channel.Scale -> {
                            add(
                                EntityData(Field.SCALE, EntityDataTypes.VECTOR3F, keyframe.value.vector3f()),
                            )
                        }
                        Keyframe.Channel.Rotation ->
                            {
                                add(
                                    EntityData(
                                        Field.LEFT_ROTATION,
                                        EntityDataTypes.QUATERNION,
                                        keyframe.value
                                            .offset(part.rotation)
                                            .radians()
                                            .quaternion(),
                                    ),
                                )
                            }
                    }

                    add(EntityData(Field.INTERPOLATION_DELAY, EntityDataTypes.INT, 0))
                    if (interpolationDuration != null) {
                        add(EntityData(Field.INTERPOLATION_DURATION, EntityDataTypes.INT, interpolationDuration))
                    }
                },
            ),
        )
    }

    private fun Part.Model.itemStack() =
        when (this) {
            is Part.Model.ItemModel -> {
                ItemStack
                    .builder()
                    .type(
                        SpigotConversionUtil.fromBukkitItemMaterial(Material.STICK),
                    ).nbt("item_model", NBTString(itemModel))
                    .build()
            }
            is Part.Model.CustomModelData -> {
                ItemStack
                    .builder()
                    .type(
                        SpigotConversionUtil.fromBukkitItemMaterial(material),
                    ).nbt("CustomModelData", NBTInt(customModelData))
                    .build()
            }
            is Part.Model.Both -> {
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

    private fun Vector.offset(position: Vector) = clone().add(position)

    private fun Vector.vector3f() = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())

    private fun Vector.radians() = Vector(Math.toRadians(x), Math.toRadians(y), Math.toRadians(z))

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
        const val INTERPOLATION_DELAY = 8
        const val INTERPOLATION_DURATION = 9
        const val TRANSLATION = 11
        const val SCALE = 12
        const val LEFT_ROTATION = 13

        // https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Entity_metadata#Item_Display
        const val ITEM = 23
    }
}
