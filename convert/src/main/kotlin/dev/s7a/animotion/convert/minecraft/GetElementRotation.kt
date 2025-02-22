package dev.s7a.animotion.convert.minecraft

import dev.s7a.animotion.convert.data.MinecraftModel
import dev.s7a.animotion.convert.data.Vector3

fun getElementRotation(rotation: Vector3): Pair<Double, MinecraftModel.Element.Rotation.Axis> =
    when {
        rotation.x != 0.0 -> rotation.x to MinecraftModel.Element.Rotation.Axis.X
        rotation.y != 0.0 -> rotation.y to MinecraftModel.Element.Rotation.Axis.Y
        rotation.z != 0.0 -> rotation.z to MinecraftModel.Element.Rotation.Axis.Z
        else -> 0.0 to MinecraftModel.Element.Rotation.Axis.Y
    }
