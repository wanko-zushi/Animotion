package dev.s7a.animotion.converter.util.path

import dev.s7a.animotion.converter.util.fileSystem
import okio.Path

fun Path.delete(mustExist: Boolean = false): Boolean {
    return runCatching { fileSystem().delete(this, mustExist) }.isSuccess
}
