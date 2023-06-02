import dev.s7a.animotion.converter.Converter
import dev.s7a.animotion.converter.json.blockbench.BlockBenchModel
import dev.s7a.animotion.converter.json.minecraft.item.MinecraftItem
import dev.s7a.animotion.converter.loader.ResourcePack
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.assertEquals

class ResourcePackTest {
    private val kelpItem = MinecraftItem("minecraft:item/generated", mapOf("layer0" to "minecraft:item/kelp"))

    @Test
    fun load_robit() {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val resourcePack = ResourcePack.load("examples/robit".toPath(), json)
        val converter = Converter(resourcePack)
        assertEquals(
            listOf("kelp" to kelpItem to listOf("robit")),
            converter.items.map {
                it.key to it.value.map(BlockBenchModel::name)
            },
        )
    }
}
