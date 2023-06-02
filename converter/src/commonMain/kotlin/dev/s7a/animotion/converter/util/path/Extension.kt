package dev.s7a.animotion.converter.util.path

import okio.Path

public val Path.extension: String
    get() = name.substringAfterLast('.', "")
