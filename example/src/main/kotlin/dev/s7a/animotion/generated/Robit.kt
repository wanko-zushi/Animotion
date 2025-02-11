package dev.s7a.animotion.generated

import dev.s7a.animotion.Animotion
import dev.s7a.animotion.AnimotionModel
import dev.s7a.animotion.`data`.Animation
import dev.s7a.animotion.`data`.Part
import org.bukkit.Material
import org.bukkit.util.Vector

class Robit(
    animotion: Animotion,
) : AnimotionModel(animotion) {
    private val gear: Part =
        part("animotion:robit_0", Material.STICK, 1, Vector(0.0, 23.749999999999993, 0.0), Vector(0.0, -22.5, 0.0))

    private val body: Part = part("animotion:robit_1", Material.STICK, 2, Vector(0.0, 0.0, 0.0))

    private val leftShoulder: Part =
        part("animotion:robit_2", Material.STICK, 3, Vector(-6.0, 14.5, 0.0))

    private val leftArm: Part = part("animotion:robit_3", Material.STICK, 4, Vector(-6.0, 14.5, 0.0))

    private val rightShoulder: Part =
        part("animotion:robit_4", Material.STICK, 5, Vector(6.0, 14.5, 0.0))

    private val rightArm: Part = part("animotion:robit_5", Material.STICK, 6, Vector(6.0, 14.5, 0.0))

    private val leftLeg: Part =
        part("animotion:robit_6", Material.STICK, 7, Vector(-2.0, 6.333333333333333, 0.0))

    private val rightLeg: Part =
        part("animotion:robit_7", Material.STICK, 8, Vector(2.0, 6.333333333333333, 0.0))

    val standing: Animation =
        loopAnimation(
            1.0,
            gear to
                listOf(
                    0.0 to rotation(0.0, 0.0, 0.0),
                ),
        )

    val walking: Animation =
        loopAnimation(
            1.0,
            gear to
                listOf(
                    0.0 to rotation(0.0, 0.0, 0.0),
                    1.0 to rotation(0.0, -180.0, 0.0),
                ),
            leftShoulder to
                listOf(
                    0.0 to rotation(-45.0, 0.0, 0.0),
                    0.5 to rotation(45.0, 0.0, 0.0),
                    1.0 to rotation(-45.0, 0.0, 0.0),
                ),
            leftArm to
                listOf(
                    0.0 to rotation(-45.0, 0.0, 0.0),
                    0.5 to rotation(45.0, 0.0, 0.0),
                    1.0 to rotation(-45.0, 0.0, 0.0),
                ),
            rightShoulder to
                listOf(
                    0.0 to rotation(45.0, 0.0, 0.0),
                    0.5 to rotation(-45.0, 0.0, 0.0),
                    1.0 to rotation(45.0, 0.0, 0.0),
                ),
            rightArm to
                listOf(
                    0.0 to rotation(45.0, 0.0, 0.0),
                    0.5 to rotation(-45.0, 0.0, 0.0),
                    1.0 to rotation(45.0, 0.0, 0.0),
                ),
            leftLeg to
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
        )

    val question: Animation =
        onceAnimation(
            2.125,
            gear to
                listOf(
                    0.0 to rotation(0.0, 0.0, 0.0),
                    1.0 to rotation(0.0, -180.0, 0.0),
                    2.0 to rotation(0.0, -360.0, 0.0),
                ),
            leftShoulder to
                listOf(
                    0.25 to rotation(0.0, 0.0, 0.0),
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
        )

    val freeze: Animation =
        holdAnimation(
            2.5,
            leftArm to
                listOf(
                    0.0 to rotation(0.0, 0.0, 0.0),
                    0.48333333333333334 to rotation(-90.0, 0.0, 0.0),
                    0.48333333333333334 to scale(1.0, 1.0, 1.0),
                    1.0 to scale(1.0, 0.7, 1.0),
                    1.1333333333333333 to scale(1.0, 0.7, 1.0),
                    1.2 to position(0.0, 0.0, 0.0),
                    1.2 to scale(1.0, 1.0, 1.0),
                    1.2166666666666666 to rotation(-90.0, 0.0, 0.0),
                    1.2833333333333334 to position(0.0, 0.0, -12.0),
                    1.3333333333333333 to rotation(-17.5, 0.0, 0.0),
                    1.3666666666666667 to position(0.0, -5.5, -18.0),
                    1.45 to rotation(0.0, 0.0, 0.0),
                    1.45 to position(0.0, -15.0, -20.0),
                ),
        )
}
