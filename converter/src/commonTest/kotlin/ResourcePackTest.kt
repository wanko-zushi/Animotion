import dev.s7a.animotion.converter.Converter
import dev.s7a.animotion.converter.exception.MinecraftItemNotFoundException
import dev.s7a.animotion.converter.exception.ModelNotFoundException
import dev.s7a.animotion.converter.exception.UnsupportedPackFormatException
import dev.s7a.animotion.converter.json.minecraft.item.MinecraftItem
import dev.s7a.animotion.converter.loader.ResourcePack
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath
import util.path.tempFile
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

sealed class ResourcePackTest(private val name: String) {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    protected fun resourcePack(): ResourcePack {
        return ResourcePack.load("src/commonTest/resources/packs/$name".toPath(), json)
    }

    class Robit : ResourcePackTest("robit") {
        private val kelpItem = MinecraftItem("minecraft:item/generated", mapOf("layer0" to "minecraft:item/kelp"))
        private val robitParts = listOf("gear", "body", "left_shoulder", "left_arm", "right_shoulder", "right_arm", "left_leg", "right_leg")

        @Test
        fun load() {
            Converter(resourcePack()).run {
                assertEquals(
                    listOf(("kelp" to kelpItem) to listOf("robit" to robitParts)),
                    parts.map { (key, value) ->
                        key to value.map { (bbmodel, parts) ->
                            bbmodel.name to parts.map { it.name }
                        }
                    },
                )
                save(tempFile())
            }
        }
    }

    class OldPackFormat : ResourcePackTest("old_pack_format") {
        @Test
        fun load() {
            assertFailsWith<UnsupportedPackFormatException> {
                resourcePack()
            }.run {
                assertEquals("Unsupported pack_format: 12 (< 13)", message)
            }
        }
    }

    class MinecraftItemNotFound : ResourcePackTest("minecraft_item_not_found") {
        @Test
        fun load() {
            val resourcePack = resourcePack()
            assertFailsWith<MinecraftItemNotFoundException> {
                Converter(resourcePack)
            }.run {
                assertEquals("animotion/base/kelp.json not found", message)
            }
        }
    }

    class ModelNotFound : ResourcePackTest("model_not_found") {
        @Test
        fun load() {
            val resourcePack = resourcePack()
            assertFailsWith<ModelNotFoundException> {
                Converter(resourcePack)
            }.run {
                assertEquals("animotion/not_exist.bbmodel not found", message)
            }
        }
    }
}
