package dev.s7a.animotion.converter.util.path

import dev.s7a.animotion.converter.util.fileSystem
import okio.BufferedSource
import okio.Path

fun Path.readText(): String {
    return fileSystem().read(this, BufferedSource::readUtf8)
}
