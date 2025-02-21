package dev.s7a.animotion.convert

import dev.s7a.animotion.convert.data.animotion.Part
import dev.s7a.animotion.convert.data.blockbench.BlockBenchModel
import java.util.concurrent.atomic.AtomicInteger

fun List<BlockBenchModel>.createParts(namespace: String): Map<BlockBenchModel, List<Part>> {
    val customModelData = AtomicInteger()
    return associateWith { model ->
        Part.from(model, namespace, customModelData)
    }
}
