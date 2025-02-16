package dev.s7a.animotion.internal

import com.github.retrooper.packetevents.protocol.entity.data.EntityData
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes
import com.github.retrooper.packetevents.protocol.item.ItemStack
import com.github.retrooper.packetevents.protocol.nbt.NBTInt
import com.github.retrooper.packetevents.protocol.nbt.NBTString
import com.github.retrooper.packetevents.util.Quaternion4f
import com.github.retrooper.packetevents.util.Vector3f
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity
import dev.s7a.animotion.ModelPart
import dev.s7a.animotion.common.BaseAnimation
import dev.s7a.animotion.common.Vector3
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import io.github.retrooper.packetevents.util.SpigotReflectionUtil
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.UUID
import kotlin.math.cos
import kotlin.math.sin

internal class PartEntity(
    private val part: ModelPart,
) {
    private val entityId = SpigotReflectionUtil.generateEntityId()
    private val uniqueId = UUID.randomUUID()

    fun spawn(
        player: Player,
        location: Location,
    ): Boolean {
        if (location.world != player.world) return false
        part.parent.animotion.packetManager.sendPacket(
            player,
            listOf(
                WrapperPlayServerSpawnEntity(
                    entityId,
                    uniqueId,
                    EntityTypes.ITEM_DISPLAY,
                    SpigotConversionUtil.fromBukkitLocation(
                        location.clone().add(
                            part.position
                                .multiply(part.parent.baseScale)
                                .multiply(-1, 1, -1)
                                .rotateFromLocation(location),
                        ),
                    ),
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
                                part.itemStack(),
                            ),
                        )

                        if (part.rotation.isZero.not()) {
                            add(
                                EntityData(
                                    Field.LEFT_ROTATION,
                                    EntityDataTypes.QUATERNION,
                                    part.rotation
                                        .multiply(1, -1, -1)
                                        .radians()
                                        .quaternion(),
                                ),
                            )
                        }

                        if (part.parent.baseScale != 1.0F) {
                            add(
                                EntityData(
                                    Field.SCALE,
                                    EntityDataTypes.VECTOR3F,
                                    Vector3f(
                                        part.parent.baseScale,
                                        part.parent.baseScale,
                                        part.parent.baseScale,
                                    ),
                                ),
                            )
                        }
                    },
                ),
            ),
        )
        return true
    }

    fun remove(player: Player) {
        part.parent.animotion.packetManager.sendPacket(
            player,
            WrapperPlayServerDestroyEntities(
                entityId,
            ),
        )
    }

    fun resetTransform(player: Player) {
        part.parent.animotion.packetManager.sendPacket(
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
                        Vector3f(
                            part.parent.baseScale,
                            part.parent.baseScale,
                            part.parent.baseScale,
                        ),
                    ),
                    EntityData(
                        Field.LEFT_ROTATION,
                        EntityDataTypes.QUATERNION,
                        part.rotation
                            .multiply(1, -1, -1)
                            .radians()
                            .quaternion(),
                    ),
                ),
            ),
        )
    }

    fun transform(
        player: Player,
        keyframe: BaseAnimation.Keyframe,
        interpolationDuration: Int?,
    ) {
        part.parent.animotion.packetManager.sendPacket(
            player,
            WrapperPlayServerEntityMetadata(
                entityId,
                buildList {
                    when (keyframe.channel) {
                        BaseAnimation.Keyframe.Channel.Position -> {
                            add(
                                EntityData(
                                    Field.TRANSLATION,
                                    EntityDataTypes.VECTOR3F,
                                    keyframe.value
                                        .multiply(-1, 1, -1)
                                        .multiply(part.parent.baseScale)
                                        .vector3f(),
                                ),
                            )
                        }
                        BaseAnimation.Keyframe.Channel.Scale -> {
                            add(
                                EntityData(
                                    Field.SCALE,
                                    EntityDataTypes.VECTOR3F,
                                    keyframe.value
                                        .multiply(part.parent.baseScale)
                                        .vector3f(),
                                ),
                            )
                        }
                        BaseAnimation.Keyframe.Channel.Rotation -> {
                            add(
                                EntityData(
                                    Field.LEFT_ROTATION,
                                    EntityDataTypes.QUATERNION,
                                    keyframe.value
                                        .add(part.rotation)
                                        .multiply(1, -1, -1)
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

    private fun ModelPart.itemStack() =
        ItemStack
            .builder()
            .type(
                SpigotConversionUtil.fromBukkitItemMaterial(part.parent.animotion.material),
            ).nbt("item_model", NBTString(itemModel))
            .nbt("CustomModelData", NBTInt(customModelData))
            .build()

    private fun Location.add(other: Vector3) = clone().add(other.x, other.y, other.z)

    private fun Vector3.vector3f() = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())

    private fun Vector3.radians() = Vector3(Math.toRadians(x), Math.toRadians(y), Math.toRadians(z))

    private fun Vector3.quaternion(): Quaternion4f {
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

    private fun Vector3.rotateFromLocation(location: Location) =
        rotate(Math.toRadians(location.yaw.toDouble()), Math.toRadians(location.pitch.toDouble()))

    private fun Vector3.rotate(
        yaw: Double,
        pitch: Double,
    ): Vector3 {
        val cosYaw = cos(yaw)
        val sinYaw = sin(yaw)
        val cosPitch = cos(pitch)
        val sinPitch = sin(pitch)

        return Vector3(
            x * cosYaw - (y * sinPitch + z * cosPitch) * sinYaw,
            y * cosPitch - z * sinPitch,
            x * sinYaw + (y * sinPitch + z * cosPitch) * cosYaw,
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
