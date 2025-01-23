import dev.s7a.animotion.AnimotionModel
import dev.s7a.animotion.AnimotionModelLoader
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class AnimotionModelEncodeTest {
    private inline fun <reified Material> AnimotionModel<Material>.save(): String =
        AnimotionModelLoader<Material>(
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            },
        ).save(this)

    @Test
    fun empty() {
        assertEquals(
            """
            {
                "version": 1,
                "data": {
                    "parts": [
                    ]
                }
            }
            """.trimIndent(),
            AnimotionModel.new<String>(listOf(), mapOf()).save(),
        )
    }
}
