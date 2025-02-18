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
            20,
            timeline(gear) {
                rotation(0, 0.0, 0.0, 0.0, Linear)
            },
        )

    val walking: ModelAnimation =
        loopAnimation(
            20,
            timeline(gear) {
                rotation(0, 0.0, 0.0, 0.0, Linear)
                rotation(20, 0.0, -180.0, 0.0, Linear)
            },
            timeline(leftShoulder) {
                rotation(0, -45.0, 0.0, 0.0, Linear)
                rotation(10, 45.0, 0.0, 0.0, Linear)
                rotation(20, -45.0, 0.0, 0.0, Linear)
            },
            timeline(leftArm) {
                rotation(0, -45.0, 0.0, 0.0, Linear)
                rotation(10, 45.0, 0.0, 0.0, Linear)
                rotation(20, -45.0, 0.0, 0.0, Linear)
            },
            timeline(rightShoulder) {
                rotation(0, 45.0, 0.0, 0.0, Linear)
                rotation(10, -45.0, 0.0, 0.0, Linear)
                rotation(20, 45.0, 0.0, 0.0, Linear)
            },
            timeline(rightArm) {
                rotation(0, 45.0, 0.0, 0.0, Linear)
                rotation(10, -45.0, 0.0, 0.0, Linear)
                rotation(20, 45.0, 0.0, 0.0, Linear)
            },
            timeline(leftLeg) {
                rotation(0, 45.0, 0.0, 0.0, Linear)
                rotation(10, -45.0, 0.0, 0.0, Linear)
                rotation(20, 45.0, 0.0, 0.0, Linear)
            },
            timeline(rightLeg) {
                rotation(0, -45.0, 0.0, 0.0, Linear)
                rotation(10, 45.0, 0.0, 0.0, Linear)
                rotation(20, -45.0, 0.0, 0.0, Linear)
            },
        )

    val question: ModelAnimation =
        onceAnimation(
            43,
            timeline(gear) {
                rotation(0, 0.0, 0.0, 0.0, Linear)
                rotation(20, 0.0, -180.0, 0.0, Linear)
                rotation(40, 0.0, -360.0, 0.0, Linear)
            },
            timeline(leftShoulder) {
                rotation(5, 0.0, 0.0, 0.0, Linear)
                rotation(14, -180.0, 0.0, 0.0, Linear)
                rotation(28, -180.0, 0.0, 0.0, Linear)
                rotation(38, 0.0, 0.0, 0.0, Linear)
            },
            timeline(leftArm) {
                rotation(0, 0.0, 0.0, 0.0, Linear)
                rotation(5, 0.0, 0.0, -25.0, Linear)
                rotation(14, -180.0, 0.0, 50.0, Linear)
                rotation(18, -180.0, 0.0, 25.0, Linear)
                rotation(20, -180.0, 0.0, 35.0, Linear)
                rotation(23, -180.0, 0.0, 25.0, Linear)
                rotation(25, -180.0, 0.0, 35.0, Linear)
                rotation(28, -180.0, 0.0, 50.0, Linear)
                rotation(38, 0.0, 0.0, 0.0, Linear)
                rotation(39, 0.0, 0.0, -5.0, Linear)
                rotation(41, 0.0, 0.0, 2.5, Linear)
                rotation(43, 0.0, 0.0, 0.0, Linear)
            },
        )

    val freeze: ModelAnimation =
        holdAnimation(
            50,
            timeline(leftArm) {
                rotation(0, 0.0, 0.0, 0.0, Linear)
                rotation(10, -90.0, 0.0, 0.0, Linear)
                scale(10, 1.0, 1.0, 1.0, Linear)
                scale(20, 1.0, 0.7, 1.0, Linear)
                scale(23, 1.0, 0.7, 1.0, Linear)
                position(24, 0.0, 0.0, 0.0, Linear)
                scale(24, 1.0, 1.0, 1.0, Linear)
                rotation(24, -90.0, 0.0, 0.0, Linear)
                position(26, 0.0, 0.0, -0.75, Linear)
                rotation(27, -17.5, 0.0, 0.0, Linear)
                position(27, 0.0, -0.34375, -1.125, Linear)
                rotation(29, 0.0, 0.0, 0.0, Linear)
                position(29, 0.0, -0.9375, -1.25, Linear)
            },
        )
}
