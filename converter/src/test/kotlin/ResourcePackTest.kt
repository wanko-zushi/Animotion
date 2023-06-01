import dev.s7a.animotion.converter.loader.ResourcePack
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.test.Test
import kotlin.test.assertNotNull

class ResourcePackTest {
    @Test
    fun load_robit() {
        val json = Json {
            ignoreUnknownKeys = true
        }
        assertNotNull(ResourcePack.load(File("examples/robit"), json))
    }
}
