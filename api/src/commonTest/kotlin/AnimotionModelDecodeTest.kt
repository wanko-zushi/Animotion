import dev.s7a.animotion.AnimotionModel
import dev.s7a.animotion.AnimotionModelLoader
import dev.s7a.animotion.exception.UnsupportedVersionException
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AnimotionModelDecodeTest {
    private inline fun <reified Material> load(text: String): AnimotionModel<Material> {
        return AnimotionModelLoader<Material>().load(text)
    }

    @Test
    fun empty() {
        assertEquals(
            AnimotionModel.new(mapOf(), mapOf()),
            load<String>(
                """
                    {
                        "version": 1,
                        "data": {
                            "parts": {
                            }
                        }
                    }
                """.trimIndent(),
            ),
        )
    }

    @Test
    fun unsupported_version() {
        assertFailsWith<UnsupportedVersionException> {
            load<String>(
                """
                    {
                        "version": 0
                    }
                """.trimIndent(),
            )
        }.let {
            assertEquals(0, it.version)
        }
    }
}
