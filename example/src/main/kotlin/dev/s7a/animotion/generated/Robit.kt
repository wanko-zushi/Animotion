package dev.s7a.animotion.generated

import dev.s7a.animotion.Animotion
import dev.s7a.animotion.AnimotionModel
import dev.s7a.animotion.ModelAnimation
import dev.s7a.animotion.ModelPart
import dev.s7a.animotion.common.BaseAnimation.Interpolation
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
            Vector3(0.0, 1.484375, 0.0),
            Vector3(0.0, -22.5, 0.0),
        )

    private val body: ModelPart =
        part(
            "animotion:robit_1",
            2,
        )

    private val leftArm: ModelPart =
        part(
            "animotion:robit_2",
            3,
            Vector3(-0.375, 0.90625, 0.0),
        )

    private val leftShoulder: ModelPart =
        part(
            "animotion:robit_3",
            4,
            Vector3(-0.375, 0.90625, 0.0),
        )

    private val rightArm: ModelPart =
        part(
            "animotion:robit_4",
            5,
            Vector3(0.375, 0.90625, 0.0),
        )

    private val rightShoulder: ModelPart =
        part(
            "animotion:robit_5",
            6,
            Vector3(0.375, 0.90625, 0.0),
        )

    private val leftLeg: ModelPart =
        part(
            "animotion:robit_6",
            7,
            Vector3(-0.125, 0.395833125, 0.0),
        )

    private val rightLeg: ModelPart =
        part(
            "animotion:robit_7",
            8,
            Vector3(0.125, 0.395833125, 0.0),
        )

    init {
        leftShoulder.children(leftArm)
        rightShoulder.children(rightArm)
    }

    val standing: ModelAnimation =
        loopAnimation(
            20,
            timeline(gear) {
                rotation(0, 0.0, 0.0, 0.0, Interpolation.Linear)
            },
        )

    val walking: ModelAnimation =
        loopAnimation(
            20,
            timeline(gear) {
                rotation(0, 0.0, 0.0, 0.0, Interpolation.Linear)
                rotation(20, 0.0, -180.0, 0.0, Interpolation.Linear)
            },
            timeline(leftShoulder) {
                rotation(0, -45.0, 0.0, 0.0, Interpolation.Linear)
                rotation(10, 45.0, 0.0, 0.0, Interpolation.Linear)
                rotation(20, -45.0, 0.0, 0.0, Interpolation.Linear)
            },
            timeline(rightShoulder) {
                rotation(0, 45.0, 0.0, 0.0, Interpolation.Linear)
                rotation(10, -45.0, 0.0, 0.0, Interpolation.Linear)
                rotation(20, 45.0, 0.0, 0.0, Interpolation.Linear)
            },
            timeline(leftLeg) {
                rotation(0, 45.0, 0.0, 0.0, Interpolation.Linear)
                rotation(10, -45.0, 0.0, 0.0, Interpolation.Linear)
                rotation(20, 45.0, 0.0, 0.0, Interpolation.Linear)
            },
            timeline(rightLeg) {
                rotation(0, -45.0, 0.0, 0.0, Interpolation.Linear)
                rotation(10, 45.0, 0.0, 0.0, Interpolation.Linear)
                rotation(20, -45.0, 0.0, 0.0, Interpolation.Linear)
            },
        )

    val question: ModelAnimation =
        onceAnimation(
            43,
            timeline(gear) {
                rotation(0, 0.0, 0.0, 0.0, Interpolation.Linear)
                rotation(20, 0.0, -180.0, 0.0, Interpolation.Linear)
                rotation(40, 0.0, -360.0, 0.0, Interpolation.Linear)
            },
            timeline(leftArm) {
                rotation(0, 0.0, 0.0, 0.0, Interpolation.Linear)
                rotation(5, 0.0, 0.0, -25.0, Interpolation.Linear)
                rotation(9, 0.0, 0.0, 1.14, Interpolation.Linear)
                rotation(14, 0.0, 0.0, -50.0, Interpolation.Linear)
                rotation(18, 0.0, 0.0, -25.0, Interpolation.Linear)
                rotation(20, 0.0, 0.0, -35.0, Interpolation.Linear)
                rotation(23, 0.0, 0.0, -25.0, Interpolation.Linear)
                rotation(25, 0.0, 0.0, -35.0, Interpolation.Linear)
                rotation(28, 0.0, 0.0, -50.0, Interpolation.Linear)
                rotation(33, 0.0, 0.0, 2.27, Interpolation.Linear)
                rotation(38, 0.0, 0.0, 0.0, Interpolation.Linear)
                rotation(39, 0.0, 0.0, -5.0, Interpolation.Linear)
                rotation(41, 0.0, 0.0, 2.5, Interpolation.Linear)
                rotation(43, 0.0, 0.0, 0.0, Interpolation.Linear)
            },
            timeline(leftShoulder) {
                rotation(5, 0.0, 0.0, 0.0, Interpolation.Linear)
                rotation(14, -180.0, 0.0, 0.0, Interpolation.Linear)
                rotation(28, -180.0, 0.0, 0.0, Interpolation.Linear)
                rotation(38, 0.0, 0.0, 0.0, Interpolation.Linear)
            },
        )

    val freeze: ModelAnimation =
        holdAnimation(
            50,
            timeline(leftArm) {
                rotation(0, 0.0, 0.0, 0.0, Interpolation.Linear)
                rotation(10, 0.0, 0.0, 0.0, Interpolation.Linear)
                scale(10, 1.0, 1.0, 1.0, Interpolation.Linear)
                scale(20, 1.0, 0.7, 1.0, Interpolation.Linear)
                scale(23, 1.0, 0.7, 1.0, Interpolation.Linear)
                position(24, 0.0, 0.0, 0.0, Interpolation.Linear)
                scale(24, 1.0, 1.0, 1.0, Interpolation.Linear)
                rotation(24, 0.0, 0.0, 0.0, Interpolation.Linear)
                rotation(26, 45.0, 0.0, 0.0, Interpolation.Linear)
                position(26, 0.0, -0.75, 0.0, Interpolation.Linear)
                rotation(27, 72.5, 0.0, 0.0, Interpolation.Linear)
                position(27, 0.0, -1.125, 0.34375, Interpolation.Linear)
                rotation(29, 90.0, 0.0, 0.0, Interpolation.Linear)
                position(29, 0.0, -1.25, 0.9375, Interpolation.Linear)
            },
            timeline(leftShoulder) {
                rotation(0, 0.0, 0.0, 0.0, Interpolation.Linear)
                rotation(10, -90.0, 0.0, 0.0, Interpolation.Linear)
            },
        )
}
