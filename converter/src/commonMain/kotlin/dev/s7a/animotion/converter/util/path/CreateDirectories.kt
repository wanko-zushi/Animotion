package dev.s7a.animotion.converter.util.path

import dev.s7a.animotion.converter.util.fileSystem
import okio.Path

fun Path.createDirectories(mustCreate: Boolean = false): Boolean {
    return runCatching { fileSystem().createDirectories(this, mustCreate) }.isSuccess
}
