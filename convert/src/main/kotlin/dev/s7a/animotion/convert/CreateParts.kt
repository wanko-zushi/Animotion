package dev.s7a.animotion.convert

import dev.s7a.animotion.convert.data.BlockbenchModel
import java.util.concurrent.atomic.AtomicInteger

fun List<BlockbenchModel>.createParts(): Map<BlockbenchModel, List<Part>> {
    val customModelData = AtomicInteger()
    return associateWith { model ->
        Part.from(model, customModelData)
    }
}
