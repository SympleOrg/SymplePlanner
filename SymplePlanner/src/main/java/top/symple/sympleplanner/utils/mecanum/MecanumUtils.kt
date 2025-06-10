package top.symple.sympleplanner.utils.mecanum;

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

fun mecanumWheelSpeeds(vx: Double, vy: Double, omega: Double): DoubleArray {
        // Wheel speed mapping (FL, FR, BL, BR)
        val fl = vx + vy + omega
        val fr = vx - vy - omega
        val bl = vx - vy + omega
        val br = vx + vy - omega

        val max = listOf(fl, fr, bl, br).maxOf { abs(it) }
        val scale = if (max > 1.0) 1.0 / max else 1.0

        return doubleArrayOf(fl, fr, bl, br).map { it * scale }.toDoubleArray()
    }

fun fieldToRobotRelativeVelocity(vx: Double, vy: Double, heading: Double): Pair<Double, Double> {
    val cos = cos(-heading)
    val sin = sin(-heading)

    val robotVx = vx * cos - vy * sin
    val robotVy = vx * sin + vy * cos

    return Pair(robotVx, robotVy)
}
