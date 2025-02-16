package dev.s7a.animotion.convert.util

fun String.toCamelCase() = toPascalCase().replaceFirstChar(Char::lowercase)
