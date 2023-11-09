package dev.s7a.animotion.converter.util.path

import okio.Path

val Path.extension: String
    get() = name.substringAfterLast('.', "")
