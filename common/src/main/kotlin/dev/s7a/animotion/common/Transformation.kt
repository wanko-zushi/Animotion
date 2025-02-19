package dev.s7a.animotion.common

data class Transformation(
    val position: Vector3?,
    val scale: Vector3?,
    val rotation: Quaternion?,
) {
    val isNotNull
        get() = position != null || scale != null || rotation != null

    companion object {
        fun create(
            parent: Transformation?,
            position: Vector3?,
            scale: Vector3?,
            rotation: Quaternion?,
        ): Transformation =
            Transformation(
                if (parent?.position != null || position != null) {
                    (parent?.position ?: DefaultPosition).run {
                        if (position != null) {
                            val rotatedPosition = parent?.rotation?.rotate(position) ?: position
                            this + rotatedPosition
                        } else {
                            this
                        }
                    }
                } else {
                    null
                },
                if (parent?.scale != null || scale != null) {
                    (parent?.scale ?: DefaultScale) * (scale ?: DefaultScale)
                } else {
                    null
                },
                if (parent?.rotation != null || rotation != null) {
                    (parent?.rotation ?: Quaternion()) * (rotation ?: Quaternion())
                } else {
                    null
                },
            )

        val DefaultPosition = Vector3()

        val DefaultRotation = Vector3()

        val DefaultScale = Vector3(1.0, 1.0, 1.0)
    }
}
