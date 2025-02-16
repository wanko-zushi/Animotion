package dev.s7a.animotion.generated

import dev.s7a.animotion.Animotion
import dev.s7a.animotion.AnimotionModel
import dev.s7a.animotion.ModelAnimation
import dev.s7a.animotion.ModelPart
import dev.s7a.animotion.common.Vector3
import kotlin.Float

class Robit(
    animotion: Animotion,
    baseScale: Float = 1.0F,
) : AnimotionModel(animotion, baseScale) {
    private val gear: ModelPart =
        part(
            "animotion:robit_0",
            1,
            Vector3(0.0, 1.4843749999999996, 0.0),
            Vector3(0.0, -22.5, 0.0),
        )

    private val body: ModelPart =
        part(
            "animotion:robit_1",
            2,
        )

    private val leftShoulder: ModelPart =
        part(
            "animotion:robit_2",
            3,
            Vector3(-0.375, 0.90625, 0.0),
        )

    private val leftArm: ModelPart =
        part(
            "animotion:robit_3",
            4,
            Vector3(-0.375, 0.90625, 0.0),
        )

    private val rightShoulder: ModelPart =
        part(
            "animotion:robit_4",
            5,
            Vector3(0.375, 0.90625, 0.0),
        )

    private val rightArm: ModelPart =
        part(
            "animotion:robit_5",
            6,
            Vector3(0.375, 0.90625, 0.0),
        )

    private val leftLeg: ModelPart =
        part(
            "animotion:robit_6",
            7,
            Vector3(-0.125, 0.3958333333333333, 0.0),
        )

    private val rightLeg: ModelPart =
        part(
            "animotion:robit_7",
            8,
            Vector3(0.125, 0.3958333333333333, 0.0),
        )

    val standing: ModelAnimation =
        loopAnimation(
            1.0,
            gear to
                listOf(
                    0.0 to rotation(0.0, 0.0, 0.0),
                ),
        )

    val walking: ModelAnimation =
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

    val question: ModelAnimation =
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

    val freeze: ModelAnimation =
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
                    1.2833333333333334 to position(0.0, 0.0, -0.75),
                    1.3333333333333333 to rotation(-17.5, 0.0, 0.0),
                    1.3666666666666667 to position(0.0, -0.34375, -1.125),
                    1.45 to rotation(0.0, 0.0, 0.0),
                    1.45 to position(0.0, -0.9375, -1.25),
                ),
        )
}
