package util

import java.io.File
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.fail

fun assertFileContent(
    expected: File,
    actual: File,
) {
    when (val extension = expected.extension) {
        "json", "mcmeta" -> {
            assertEquals(expected.readText(), actual.readText(), actual.name)
        }
        "png" -> {
            assertContentEquals(expected.readBytes(), actual.readBytes(), actual.name)
        }
        else -> {
            fail("Unsupported file extension $extension")
        }
    }
}
