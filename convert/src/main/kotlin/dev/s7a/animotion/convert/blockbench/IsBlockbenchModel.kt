package dev.s7a.animotion.convert.blockbench

import java.io.File

val File.isBlockbenchModel
    get() = extension == "bbmodel"
