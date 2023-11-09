package dev.s7a.animotion.converter.util.path

import okio.IOException
import okio.Path

private fun constructMessage(file: Path, other: Path?, reason: String?): String {
    val sb = StringBuilder(file.toString())
    if (other != null) {
        sb.append(" -> $other")
    }
    if (reason != null) {
        sb.append(": $reason")
    }
    return sb.toString()
}

open class FileSystemException(
    val file: Path,
    val other: Path? = null,
    val reason: String? = null,
) : IOException(constructMessage(file, other, reason))

class FileAlreadyExistsException(
    file: Path,
    other: Path? = null,
    reason: String? = null,
) : FileSystemException(file, other, reason)

class NoSuchFileException(
    file: Path,
    other: Path? = null,
    reason: String? = null,
) : FileSystemException(file, other, reason)
