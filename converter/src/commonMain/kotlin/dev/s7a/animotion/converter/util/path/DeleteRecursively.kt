package dev.s7a.animotion.converter.util.path

import dev.s7a.animotion.converter.util.fileSystem
import okio.Path

fun Path.deleteRecursively(mustExist: Boolean = false): Boolean {
    return runCatching { fileSystem().deleteRecursively(this, mustExist) }.isSuccess
}
