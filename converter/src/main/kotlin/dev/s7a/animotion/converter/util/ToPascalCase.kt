package dev.s7a.animotion.converter.util

fun String.toPascalCase() =
    split("_").joinToString("") {
        it.replaceFirstChar(Char::uppercase)
    }
