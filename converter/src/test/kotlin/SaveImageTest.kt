import dev.s7a.animotion.converter.data.blockbench.Texture
import util.assertFileContent
import java.io.File
import kotlin.io.path.createTempFile
import kotlin.test.Test

class SaveImageTest {
    @Test
    fun saveImage() {
        val base64 = File("src/test/resources/expected/image/base64.txt").readText()
        val texture = Texture(base64)
        val file = createTempFile().toFile()
        texture.saveTo(file)
        assertFileContent(File("src/test/resources/expected/image/robit.png"), file)
    }
}
