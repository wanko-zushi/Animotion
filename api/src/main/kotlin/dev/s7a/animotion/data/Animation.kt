package dev.s7a.animotion.data

data class Animation(
    val type: Type,
    val length: Double,
    val animators: Map<Part, List<Pair<Double, Keyframe>>>,
) {
    enum class Type {
        Loop,
        Once,
        Hold,
    }
}
