package dev.s7a.animotion.converter.util.path

import dev.s7a.animotion.converter.util.fileSystem
import okio.Path

fun Path.writeBytes(array: ByteArray) {
    fileSystem().write(this) {
        write(array)
    }
}
