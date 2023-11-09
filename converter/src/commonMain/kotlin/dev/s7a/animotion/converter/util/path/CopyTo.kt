package dev.s7a.animotion.converter.util.path

import dev.s7a.animotion.converter.util.fileSystem
import okio.Path

fun Path.copyTo(target: Path, overwrite: Boolean = false): Path {
    if (!this.exists()) {
        throw NoSuchFileException(file = this, reason = "The source file doesn't exist.")
    }

    if (target.exists()) {
        if (!overwrite) {
            throw FileAlreadyExistsException(file = this, other = target, reason = "The destination file already exists.")
        } else if (!target.delete()) {
            throw FileAlreadyExistsException(file = this, other = target, reason = "Tried to overwrite the destination, but failed to delete it.")
        }
    }

    if (this.isDirectory) {
        if (!target.createDirectories()) {
            throw FileSystemException(file = this, other = target, reason = "Failed to create target directory.")
        }
    } else {
        target.parent?.createDirectories()

        fileSystem().copy(this, target)
    }

    return target
}
