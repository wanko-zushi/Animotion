package dev.s7a.animotion.convert

import dev.s7a.animotion.convert.data.animotion.Part
import dev.s7a.animotion.convert.data.blockbench.BlockbenchModel
import java.util.concurrent.atomic.AtomicInteger

fun List<BlockbenchModel>.createParts(namespace: String): Map<BlockbenchModel, List<Part>> {
    val customModelData = AtomicInteger()
    return associateWith { model ->
        Part.from(model, namespace, customModelData)
    }
}
