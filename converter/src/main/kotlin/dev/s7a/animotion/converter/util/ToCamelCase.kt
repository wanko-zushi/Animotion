package dev.s7a.animotion.converter.util

fun String.toCamelCase() = toPascalCase().replaceFirstChar(Char::lowercase)
