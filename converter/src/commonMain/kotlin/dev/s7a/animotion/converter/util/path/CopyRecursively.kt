package dev.s7a.animotion.converter.util.path

import okio.IOException
import okio.Path

fun Path.copyRecursively(
    target: Path,
    overwrite: Boolean = false,
    onError: (Path, IOException) -> OnErrorAction = { _, exception -> throw exception },
): Boolean {
    if (!exists()) {
        return onError(this, NoSuchFileException(file = this, reason = "The source file doesn't exist.")) != OnErrorAction.TERMINATE
    }
    return runCatching {
        for (src in listRecursively()) {
            if (!src.exists()) {
                if (onError(src, NoSuchFileException(file = src, reason = "The source file doesn't exist.")) == OnErrorAction.TERMINATE) {
                    return false
                }
            } else {
                val relPath = src.relativeTo(this)
                val dst = target / relPath
                if (dst.exists() && !(src.isDirectory && dst.isDirectory)) {
                    val stillExists = when {
                        !overwrite -> true
                        dst.isDirectory -> !dst.deleteRecursively()
                        else -> !dst.delete()
                    }

                    if (stillExists) {
                        if (onError(dst, FileAlreadyExistsException(src, dst, "The destination file already exists.")) == OnErrorAction.TERMINATE) {
                            return false
                        }

                        continue
                    }
                }

                if (src.isDirectory) {
                    dst.createDirectories()
                } else if (src.copyTo(dst, overwrite).length() != src.length()) {
                    if (onError(src, IOException("Source file wasn't copied completely, length of destination file differs.")) == OnErrorAction.TERMINATE) {
                        return false
                    }
                }
            }
        }
    }.isSuccess
}
