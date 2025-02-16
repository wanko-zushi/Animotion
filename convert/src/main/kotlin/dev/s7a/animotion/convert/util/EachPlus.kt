package dev.s7a.animotion.convert.util

fun List<Double>.eachPlus(other: List<Double>): List<Double> {
    require(size == other.size)
    return mapIndexed { index, value ->
        value.plus(other[index])
    }
}
