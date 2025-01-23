package util

import java.io.File
import kotlin.test.assertContentEquals

fun assertFileContent(expected: File, actual: File) {
    assertContentEquals(expected.readBytes(), actual.readBytes())
}
