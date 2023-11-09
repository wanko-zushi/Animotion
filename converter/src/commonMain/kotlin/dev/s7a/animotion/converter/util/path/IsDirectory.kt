package dev.s7a.animotion.converter.util.path

import dev.s7a.animotion.converter.util.fileSystem
import okio.Path

val Path.isDirectory
    get() = fileSystem().metadata(this).isDirectory
