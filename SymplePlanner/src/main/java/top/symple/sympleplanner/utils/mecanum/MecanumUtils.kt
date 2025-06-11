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
    val cosValue = cos(-heading)
    val sinValue = sin(-heading)

    val robotVx = vx * cosValue - vy * sinValue
    val robotVy = vx * sinValue + vy * cosValue

    return Pair(robotVx, robotVy)
}
