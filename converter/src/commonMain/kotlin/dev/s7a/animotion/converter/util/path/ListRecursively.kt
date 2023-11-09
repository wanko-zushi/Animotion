package dev.s7a.animotion.converter.util.path

import dev.s7a.animotion.converter.util.fileSystem
import okio.Path

fun Path.listRecursively(followSymlinks: Boolean = false): Sequence<Path> {
    return fileSystem().listRecursively(this, followSymlinks)
}
