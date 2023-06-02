import dev.s7a.animotion.converter.loader.ResourcePack
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.assertEquals

class ResourcePackTest {
    @Test
    fun load_robit() {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val resourcePack = ResourcePack.load("examples/robit".toPath(), json)
        val models = resourcePack.animotion.models.single().toMinecraftModels(resourcePack.animotion.settings)
        assertEquals(8, models.size)
    }
}
