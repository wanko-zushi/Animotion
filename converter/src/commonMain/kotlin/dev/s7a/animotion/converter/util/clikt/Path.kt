package dev.s7a.animotion.converter.util.clikt

import com.github.ajalt.clikt.parameters.options.RawOption
import com.github.ajalt.clikt.parameters.options.convert
import okio.Path.Companion.toPath

fun RawOption.path(normalize: Boolean = false) = convert { it.toPath(normalize) }
