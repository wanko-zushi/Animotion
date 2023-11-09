package dev.s7a.animotion.converter.util.clikt

import com.github.ajalt.clikt.parameters.options.NullableOption
import com.github.ajalt.clikt.parameters.options.RawOption
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.validate
import dev.s7a.animotion.converter.util.path.exists
import dev.s7a.animotion.converter.util.path.isDirectory
import okio.Path
import okio.Path.Companion.toPath

fun RawOption.path(normalize: Boolean = false) = convert { it.toPath(normalize) }

fun NullableOption<Path, Path>.directory() = validate {
    require(!it.exists() || it.isDirectory) { "value must be directory path" }
}

fun NullableOption<Path, Path>.existingDirectory() = validate {
    require(it.exists()) { "no such file or directory" }
    require(it.isDirectory) { "value must be directory path" }
}
