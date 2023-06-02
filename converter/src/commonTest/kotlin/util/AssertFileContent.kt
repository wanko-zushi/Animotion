package util

import okio.Path
import util.path.readBytes
import kotlin.test.assertContentEquals

fun assertFileContent(expected: Path, actual: Path) {
    assertContentEquals(expected.readBytes(), actual.readBytes())
}
