package dev.s7a.animotion.converter.util

import okio.FileSystem

actual fun fileSystem(): FileSystem {
    return FileSystem.SYSTEM
}