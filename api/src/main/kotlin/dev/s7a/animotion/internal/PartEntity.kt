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
import dev.s7a.animotion.common.Quaternion
import dev.s7a.animotion.common.Transformation
import dev.s7a.animotion.common.Vector3
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import io.github.retrooper.packetevents.util.SpigotReflectionUtil
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.UUID

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
        part.model.animotion.packetManager.sendPacket(
            player,
            listOf(
                WrapperPlayServerSpawnEntity(
                    entityId,
                    uniqueId,
                    EntityTypes.ITEM_DISPLAY,
                    SpigotConversionUtil.fromBukkitLocation(location),
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

                        if (part.position.isZero.not()) {
                            add(
                                EntityData(
                                    Field.TRANSLATION,
                                    EntityDataTypes.VECTOR3F,
                                    part.position.vector3f(),
                                ),
                            )
                        }

                        if (part.rotation.isZero.not()) {
                            add(
                                EntityData(
                                    Field.LEFT_ROTATION,
                                    EntityDataTypes.QUATERNION,
                                    part.rotation
                                        .toRadians()
                                        .quaternion()
                                        .quaternion4f(),
                                ),
                            )
                        }

                        if (part.model.baseScale != 1.0F) {
                            add(
                                EntityData(
                                    Field.SCALE,
                                    EntityDataTypes.VECTOR3F,
                                    Vector3f(
                                        part.model.baseScale,
                                        part.model.baseScale,
                                        part.model.baseScale,
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
        part.model.animotion.packetManager.sendPacket(
            player,
            WrapperPlayServerDestroyEntities(
                entityId,
            ),
        )
    }

    fun resetTransform(player: Player) {
        part.model.animotion.packetManager.sendPacket(
            player,
            WrapperPlayServerEntityMetadata(
                entityId,
                listOf(
                    EntityData(
                        Field.TRANSLATION,
                        EntityDataTypes.VECTOR3F,
                        part.position.vector3f(),
                    ),
                    EntityData(
                        Field.SCALE,
                        EntityDataTypes.VECTOR3F,
                        Vector3f(
                            part.model.baseScale,
                            part.model.baseScale,
                            part.model.baseScale,
                        ),
                    ),
                    EntityData(
                        Field.LEFT_ROTATION,
                        EntityDataTypes.QUATERNION,
                        part.rotation
                            .toRadians()
                            .quaternion()
                            .quaternion4f(),
                    ),
                    EntityData(
                        Field.INTERPOLATION_DURATION,
                        EntityDataTypes.INT,
                        0,
                    ),
                ),
            ),
        )
    }

    fun transform(
        player: Player,
        transformation: Transformation,
    ) {
        transform(
            player,
            transformation.position,
            transformation.scale,
            transformation.rotation,
        )
    }

    private fun transform(
        player: Player,
        position: Vector3?,
        scale: Vector3?,
        rotation: Quaternion?,
    ) {
        part.model.animotion.packetManager.sendPacket(
            player,
            WrapperPlayServerEntityMetadata(
                entityId,
                buildList {
                    if (position != null) {
                        add(
                            EntityData(
                                Field.TRANSLATION,
                                EntityDataTypes.VECTOR3F,
                                position.multiply(part.model.baseScale).multiply(-1, 1, 1).vector3f(),
                            ),
                        )
                    }
                    if (scale != null) {
                        add(
                            EntityData(
                                Field.SCALE,
                                EntityDataTypes.VECTOR3F,
                                scale.multiply(part.model.baseScale).vector3f(),
                            ),
                        )
                    }
                    if (rotation != null) {
                        add(
                            EntityData(
                                Field.LEFT_ROTATION,
                                EntityDataTypes.QUATERNION,
                                (rotation * part.rotation.toRadians().quaternion()).quaternion4f(),
                            ),
                        )
                    }

                    add(EntityData(Field.INTERPOLATION_DURATION, EntityDataTypes.INT, 1))
                },
            ),
        )
    }

    private fun ModelPart.itemStack() =
        ItemStack
            .builder()
            .type(
                SpigotConversionUtil.fromBukkitItemMaterial(part.model.animotion.material),
            ).nbt("item_model", NBTString(itemModel))
            .nbt("CustomModelData", NBTInt(customModelData))
            .build()

    private fun Vector3.vector3f() = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())

    private fun Quaternion.quaternion4f(): Quaternion4f =
        Quaternion4f(
            x.toFloat(),
            y.toFloat(),
            z.toFloat(),
            w.toFloat(),
        )

    private object Field {
        // https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Entity_metadata#Display
        const val INTERPOLATION_DURATION = 9
        const val TRANSLATION = 11
        const val SCALE = 12
        const val LEFT_ROTATION = 13

        // https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Entity_metadata#Item_Display
        const val ITEM = 23
    }
}
