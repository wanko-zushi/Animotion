package dev.s7a.animotion.common

/**
 * Represents a 3D transformation consisting of position, scale, and rotation.
 *
 * @property position The 3D position vector of the transformation, or null if unspecified.
 * @property scale The scaling factor as a vector, or null if unspecified.
 * @property rotation The rotational quaternion representing orientation, or null if unspecified.
 */
data class Transformation(
    val position: Vector3?,
    val scale: Vector3?,
    val rotation: Quaternion?,
) {
    /**
     * Returns true if at least one of the position, scale, or rotation properties is not null.
     */
    val isNotNull
        get() = position != null || scale != null || rotation != null

    companion object {
        /**
         * Creates a new [Transformation] by combining the given components, with optional
         * inheritance from a parent transformation.
         *
         * @param parent An optional transformation to inherit values for unspecified components.
         * @param position The new position vector, or null to inherit the parent's position.
         * @param scale The new scale vector, or null to inherit the parent's scale.
         * @param rotation The new rotation quaternion, or null to inherit the parent's rotation.
         * @return A new [Transformation] that combines the specified and inherited components.
         */
        fun create(
            parent: Transformation?,
            part: BasePart,
            position: Vector3?,
            scale: Vector3?,
            rotation: Quaternion?,
        ): Transformation =
            Transformation(
                if (part.position.isZero.not() || parent?.position != null || position != null) {
                    val parentPosition = parent?.position ?: part.position
                    val rotatedPosition = position?.let { parent?.rotation?.rotate(it) } ?: position ?: DefaultPosition
                    parentPosition + rotatedPosition
                } else {
                    null
                },
                if (parent?.scale != null || scale != null) {
                    (parent?.scale ?: DefaultScale) * (scale ?: DefaultScale)
                } else {
                    null
                },
                if (parent?.rotation != null || rotation != null) {
                    val parentRotation = parent?.rotation ?: Quaternion()
                    parentRotation * (rotation ?: Quaternion())
                } else {
                    null
                },
            )

        /**
         * The default position vector, initialized as (0, 0, 0).
         */
        val DefaultPosition = Vector3()

        /**
         * The default rotation vector, initialized as (0, 0, 0).
         */
        val DefaultRotation = Vector3()

        /**
         * The default scale vector, initialized as (1, 1, 1).
         */
        val DefaultScale = Vector3(1.0, 1.0, 1.0)
    }
}
