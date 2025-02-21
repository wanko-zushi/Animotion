package dev.s7a.animotion.convert.util

import java.io.File

fun File.createParentDirectory() = parentFile?.mkdirs() ?: false
