package util.path

import dev.s7a.animotion.converter.util.fileSystem
import okio.FileSystem
import okio.Path
import kotlin.random.Random
import kotlin.random.nextULong

fun tempFile(prefix: String = "temp", suffix: String = ""): Path {
    val directory = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("animotion")
    fileSystem().createDirectory(directory)
    return directory.resolve(prefix + Random.nextULong() + suffix)
}
