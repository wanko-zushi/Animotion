package dev.s7a.animotion.internal

import dev.s7a.animotion.common.BaseAnimation
import dev.s7a.animotion.common.Vector3

/**
 * Interpolates a channel of keyframes to generate intermediate values based on interpolation methods.
 *
 * @param channel A map of timestamps to keyframe entries, where each entry stores a keyframe value and interpolation type.
 * @return A map of timestamps to interpolated `Vector3` values, including intermediate points.
 */
internal fun interpolateChannel(channel: Map<Long, BaseAnimation.Timeline.Entry>): Map<Long, Vector3> {
    if (channel.size < 2) {
        return channel.entries.associate { (key, value) ->
            key to value.keyframe
        }
    }
    val sortedEntries = channel.toSortedMap()
    val result = mutableMapOf<Long, Vector3>()
    val entriesList = sortedEntries.entries.toList()
    for (i in 0 until entriesList.size - 1) {
        val (t1, entry1) = entriesList[i]
        val (t2, entry2) = entriesList[i + 1]
        result[t1] = entry1.keyframe
        val p0 = if (i > 0) entriesList[i - 1].value.keyframe else entry1.keyframe
        val p1 = entry1.keyframe
        val p2 = entry2.keyframe
        val p3 = if (i < entriesList.size - 2) entriesList[i + 2].value.keyframe else entry2.keyframe
        val ticksDiff = t2 - t1
        for (t in 1 until ticksDiff) {
            val alpha = t.toDouble() / ticksDiff
            val interpolatedValue =
                when (entry1.interpolation) {
                    BaseAnimation.Interpolation.Linear -> linearInterpolation(p1, p2, alpha)
                    BaseAnimation.Interpolation.Catmullrom -> catmullRomInterpolation(p0, p1, p2, p3, alpha)
                }
            result[t1 + t] = interpolatedValue
        }
    }
    val last = entriesList.last()
    result[last.key] = last.value.keyframe
    return result
}

private fun linearInterpolation(
    start: Vector3,
    end: Vector3,
    alpha: Double,
): Vector3 = start * (1 - alpha) + end * alpha

private fun catmullRomInterpolation(
    p0: Vector3,
    p1: Vector3,
    p2: Vector3,
    p3: Vector3,
    t: Double,
): Vector3 {
    val t2 = t * t
    val t3 = t2 * t

    val a0 = p1 * 2.0
    val a1 = p2 - p0
    val a2 = p0 * 2.0 - p1 * 5.0 + p2 * 4.0 - p3
    val a3 = -p0 + p1 * 3.0 - p2 * 3.0 + p3

    return (a0 + a1 * t + a2 * t2 + a3 * t3) * 0.5
}
