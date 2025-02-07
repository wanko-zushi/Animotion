package dev.s7a.animotion

import org.bukkit.Material
import org.bukkit.util.Vector

class Robit(
    animotion: Animotion,
) : AnimotionModel(animotion) {
    private val gear = part("animotion:robit/gear", Material.KELP, 1, Vector(0.0, 1.4843749999999996, 0.0), Vector(0.0, -22.5, 0.0))
    private val body = part("animotion:robit/body", Material.KELP, 2)
    private val leftShoulder = part("animotion:robit/left_shoulder", Material.KELP, 3, Vector(-0.375, 0.90625, 0.0))
    private val leftArm = part("animotion:robit/left_arm", Material.KELP, 4, Vector(-0.375, 0.90625, 0.0))
    private val rightShoulder = part("animotion:robit/right_shoulder", Material.KELP, 5, Vector(0.375, 0.90625, 0.0))
    private val rightArm = part("animotion:robit/right_arm", Material.KELP, 6, Vector(0.375, 0.90625, 0.0))
    private val leftLeg = part("animotion:robit/left_leg", Material.KELP, 7, Vector(-0.125, 0.3958333333333333, 0.0))
    private val rightLeg = part("animotion:robit/right_leg", Material.KELP, 8, Vector(0.125, 0.3958333333333333, 0.0))

    val standing =
        loopAnimation(
            1.0,
            gear to
                listOf(
                    0.0 to rotation(0.0, 0.0, 0.0),
                ),
        )
    val walking =
        loopAnimation(
            1.0,
            leftLeg to
                listOf(
                    0.0 to rotation(45.0, 0.0, 0.0),
                    0.5 to rotation(-45.0, 0.0, 0.0),
                    1.0 to rotation(45.0, 0.0, 0.0),
                ),
            leftShoulder to
                listOf(
                    0.0 to rotation(45.0, 0.0, 0.0),
                    0.5 to rotation(-45.0, 0.0, 0.0),
                    1.0 to rotation(45.0, 0.0, 0.0),
                ),
            leftArm to
                listOf(
                    0.0 to rotation(45.0, 0.0, 0.0),
                    0.5 to rotation(-45.0, 0.0, 0.0),
                    1.0 to rotation(45.0, 0.0, 0.0),
                ),
            rightLeg to
                listOf(
                    0.0 to rotation(-45.0, 0.0, 0.0),
                    0.5 to rotation(45.0, 0.0, 0.0),
                    1.0 to rotation(-45.0, 0.0, 0.0),
                ),
            rightShoulder to
                listOf(
                    0.0 to rotation(-45.0, 0.0, 0.0),
                    0.5 to rotation(45.0, 0.0, 0.0),
                    1.0 to rotation(-45.0, 0.0, 0.0),
                ),
            rightArm to
                listOf(
                    0.0 to rotation(-45.0, 0.0, 0.0),
                    0.5 to rotation(45.0, 0.0, 0.0),
                    1.0 to rotation(-45.0, 0.0, 0.0),
                ),
            gear to
                listOf(
                    0.0 to rotation(0.0, 0.0, 0.0),
                    1.0 to rotation(0.0, -180.0, 0.0),
                ),
        )
    val question =
        onceAnimation(
            2.125,
            leftShoulder to
                listOf(
                    0.0 to rotation(0.0, 0.0, 0.0),
                    0.7083333333333334 to rotation(-180.0, 0.0, 0.0),
                    1.4166666666666667 to rotation(-180.0, 0.0, 0.0),
                    1.875 to rotation(0.0, 0.0, 0.0),
                ),
            leftArm to
                listOf(
                    0.0 to rotation(0.0, 0.0, 0.0),
                    0.25 to rotation(0.0, 0.0, -25.0),
                    0.7083333333333334 to rotation(-180.0, 0.0, 50.0),
                    0.875 to rotation(-180.0, 0.0, 25.0),
                    1.0 to rotation(-180.0, 0.0, 35.0),
                    1.125 to rotation(-180.0, 0.0, 25.0),
                    1.25 to rotation(-180.0, 0.0, 35.0),
                    1.4166666666666667 to rotation(-180.0, 0.0, 50.0),
                    1.875 to rotation(0.0, 0.0, 0.0),
                    1.9583333333333333 to rotation(0.0, 0.0, -5.0),
                    2.0416666666666665 to rotation(0.0, 0.0, 2.5),
                    2.125 to rotation(0.0, 0.0, 0.0),
                ),
            gear to
                listOf(
                    0.0 to rotation(0.0, 0.0, 0.0),
                    1.0 to rotation(0.0, -180.0, 0.0),
                    2.0 to rotation(0.0, -360.0, 0.0),
                ),
        )
    val freeze =
        holdAnimation(
            2.5,
            leftArm to
                listOf(
                    0.0 to rotation(0.0, 0.0, 0.0),
                    0.48333333333333334 to rotation(-90.0, 0.0, 0.0),
                    0.48333333333333334 to scale(1.0, 1.0, 1.0),
                    1.0 to scale(1.0, 0.7, 1.0),
                    1.2166666666666666 to rotation(-90.0, 0.0, 0.0),
                    1.3333333333333333 to rotation(-17.5, 0.0, 0.0),
                    1.2 to position(0.0, 0.0, 0.0),
                    1.2 to scale(1.0, 1.0, 1.0),
                    1.2833333333333334 to position(0.0, 0.0, -0.75),
                    1.1333333333333333 to scale(1.0, 0.7, 1.0),
                    1.3666666666666667 to position(0.0, -0.34375, -1.125),
                    1.45 to rotation(0.0, 0.0, 0.0),
                    1.45 to position(0.0, -0.9375, -1.25),
                ),
        )
}
