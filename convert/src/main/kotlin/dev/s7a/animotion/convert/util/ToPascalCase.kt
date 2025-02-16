package dev.s7a.animotion.convert.util

fun String.toPascalCase() =
    split("_").joinToString("") {
        it.replaceFirstChar(Char::uppercase)
    }
