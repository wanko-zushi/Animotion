package dev.s7a.animotion

import org.bukkit.Material

class Robit(
    animotion: Animotion,
) : AnimotionModel(animotion) {
    val gear = part("animotion:robit/gear", Material.KELP, 1, Position(0.0, 1.4843749999999996, 0.0), Rotation(0.0, -0.3927, 0.0))
    val body = part("animotion:robit/body", Material.KELP, 2)
    val leftShoulder = part("animotion:robit/left_shoulder", Material.KELP, 3, Position(-0.375, 0.90625, 0.0))
    val leftArm = part("animotion:robit/left_arm", Material.KELP, 4, Position(-0.375, 0.90625, 0.0))
    val rightShoulder = part("animotion:robit/right_shoulder", Material.KELP, 5, Position(0.375, 0.90625, 0.0))
    val rightArm = part("animotion:robit/right_arm", Material.KELP, 6, Position(0.375, 0.90625, 0.0))
    val leftLeg = part("animotion:robit/left_leg", Material.KELP, 7, Position(-0.125, 0.3958333333333333, 0.0))
    val rightLeg = part("animotion:robit/right_leg", Material.KELP, 8, Position(0.125, 0.3958333333333333, 0.0))
}
