import dev.s7a.animotion.converter.Converter
import dev.s7a.animotion.converter.json.minecraft.item.MinecraftItem
import dev.s7a.animotion.converter.loader.ResourcePack
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath
import util.path.tempFile
import kotlin.test.Test
import kotlin.test.assertEquals

class ResourcePackTest {
    private val kelpItem = MinecraftItem("minecraft:item/generated", mapOf("layer0" to "minecraft:item/kelp"))
    private val robitParts = listOf("gear", "body", "left_shoulder", "left_arm", "right_shoulder", "right_arm", "left_leg", "right_leg")

    @Test
    fun load_robit() {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val resourcePack = ResourcePack.load("examples/robit".toPath(), json)
        val converter = Converter(resourcePack)
        assertEquals(
            listOf(("kelp" to kelpItem) to listOf("robit" to robitParts)),
            converter.parts.map { (key, value) ->
                key to value.map { (bbmodel, parts) ->
                    bbmodel.name to parts.map { it.name }
                }
            },
        )
        val directory = tempFile()
        converter.save(directory)
    }
}
