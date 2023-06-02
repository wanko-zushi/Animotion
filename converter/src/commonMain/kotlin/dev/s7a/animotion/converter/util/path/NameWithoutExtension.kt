package dev.s7a.animotion.converter.util.path

import okio.Path

val Path.nameWithoutExtension
    get() = name.substringBeforeLast(".")
