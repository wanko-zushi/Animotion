package util.path

import dev.s7a.animotion.converter.util.fileSystem
import okio.BufferedSource
import okio.Path

fun Path.readBytes(): ByteArray {
    return fileSystem().read(this, BufferedSource::readByteArray)
}
