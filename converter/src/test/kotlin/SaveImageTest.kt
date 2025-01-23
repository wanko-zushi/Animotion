import dev.s7a.animotion.converter.json.blockbench.Texture
import util.assertFileContent
import java.io.File
import kotlin.io.path.createTempDirectory
import kotlin.test.Test

class SaveImageTest {
    @Test
    fun saveImage() {
        val base64 =
            @Suppress("ktlint:standard:max-line-length")
            "data:image/png;base64,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"
        val texture = Texture(base64)
        val file = texture.getDestinationFile(createTempDirectory().toFile(), "test", "robit", 0)
        texture.saveTo(file)
        assertFileContent(File("src/test/resources/expected/robit.png"), file)
    }
}
