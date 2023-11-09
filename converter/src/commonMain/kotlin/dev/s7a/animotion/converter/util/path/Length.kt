package dev.s7a.animotion.converter.util.path

import dev.s7a.animotion.converter.util.fileSystem
import okio.Path

fun Path.length(): Long? {
    return fileSystem().metadata(this).size
}
