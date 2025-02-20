package dev.s7a.animotion.common

/**
 * Represents a 3D transformation consisting of position, scale, and rotation.
 *
 * @property position The 3D position vector of the transformation, or null if unspecified.
 * @property scale The scaling factor as a vector, or null if unspecified.
 * @property rotation The rotational quaternion representing orientation, or null if unspecified.
 * @property teleport The teleportation position vector, representing a direct movement point, or null if unspecified.
 *
 */
data class Transformation(
    val part: BasePart,
    val position: Vector3?,
    val scale: Vector3?,
    val rotation: Quaternion?,
    val teleport: Vector3,
) {
    /**
     * Returns true if at least one of the position, scale, or rotation properties is not null.
     * Specifically, checks whether any of the following components of the transformation are specified:
     * - [position]: The position vector.
     * - [scale]: The scale vector.
     * - [rotation]: The rotation quaternion.
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
                part,
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
                    val parentScale = parent?.scale ?: DefaultScale
                    val localScale = scale ?: DefaultScale
                    parentScale * localScale
                } else {
                    null
                },
                if (parent?.rotation != null || rotation != null) {
                    val parentRotation = parent?.rotation ?: Quaternion()
                    val localeRotation = rotation ?: Quaternion()
                    parentRotation * localeRotation
                } else {
                    null
                },
                if (parent != null) {
                    val positionDiff = (part.position - parent.part.position).multiply(-1, 1, -1)
                    if (parent.rotation != null) {
                        parent.rotation.rotate(positionDiff) - positionDiff
                    } else {
                        Vector3()
                    }
                } else {
                    Vector3()
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
