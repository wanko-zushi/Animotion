import dev.s7a.animotion.converter.exception.UnsupportedPackFormatException
import dev.s7a.animotion.converter.generator.PackGenerator
import dev.s7a.animotion.converter.loader.ResourcePack
import kotlinx.serialization.json.Json
import util.assertFileContent
import java.io.File
import kotlin.io.path.createTempDirectory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

sealed class ResourcePackTest(
    private val name: String,
) {
    private val json =
        Json {
            ignoreUnknownKeys = true
        }

    protected val destination: File = createTempDirectory().toFile()

    protected fun resourcePack(): ResourcePack = ResourcePack.load(File("src/test/resources/packs/$name"), json)

    protected fun assertGeneratedPack() {
        val expected = File("src/test/resources/expected/$name")
        expected.walk().forEach { file ->
            if (file.isFile) {
                assertFileContent(file, destination.resolve(file.toRelativeString(expected)))
            }
        }
    }

    class Robit : ResourcePackTest("robit") {
        @Test
        fun load() {
            PackGenerator(resourcePack()).run {
                save(destination)
                assertGeneratedPack()
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
}
