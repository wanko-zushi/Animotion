import dev.s7a.animotion.convert.InputPack
import dev.s7a.animotion.convert.exception.UnsupportedPackFormatException
import dev.s7a.animotion.convert.generator.PackGenerator
import util.assertFileContent
import java.io.File
import kotlin.io.path.createTempDirectory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

sealed class ResourcePackTest(
    private val name: String,
) {
    protected val destination: File = createTempDirectory().toFile()

    protected fun resourcePack(): InputPack = InputPack.load(File("src/test/resources/packs/$name"))

    protected fun assertGeneratedPack() {
        val expected = File("src/test/resources/expected/$name")
        val files =
            destination
                .walk()
                .filter { it.isFile }
                .associateBy { it.toRelativeString(destination) }
                .toMutableMap()
        expected
            .walk()
            .filter { it.isFile }
            .forEach {
                assertFileContent(it, files.remove(it.toRelativeString(expected)) ?: fail("File $it does not exist"))
            }
        if (files.isNotEmpty()) {
            fail("File is not generated: ${files.keys}")
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
