package util

import java.io.File
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

fun assertFileContent(expected: File, actual: File) {
    assertEquals(expected.length(), actual.length())
    assertContentEquals(expected.readBytes(), actual.readBytes())
}
