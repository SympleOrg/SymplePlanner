package top.symple.sympleplanner.geometry

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

data class Translation2d(val x: Double, val y: Double) {
    companion object {
        @JvmStatic fun zero() = Translation2d(0.0, 0.0)

        @JvmStatic fun from(x: Double, y: Double) = Translation2d(x, y)
        @JvmStatic fun fromPolar(radius: Double, theta: Double) = Translation2d(radius * cos(theta), radius * sin(theta))
        @JvmStatic fun fromPolar(radius: Double, rotation2d: Rotation2d) = Translation2d(radius * rotation2d.cosVal, radius * rotation2d.sinVal)
    }

    operator fun plus(translation2d: Translation2d) = Translation2d(x + translation2d.x, y + translation2d.y)

    operator fun minus(translation2d: Translation2d) = Translation2d(x - translation2d.x, y - translation2d.y)

    operator fun unaryMinus() = Translation2d(-x, -y)

    operator fun times(num: Double) = Translation2d(x * num, y * num)
    operator fun times(translation2d: Translation2d) = Translation2d(x * translation2d.x, y * translation2d.y)
    operator fun times(rotation2d: Rotation2d) = (rotation2d * this).asTranslation2d()
    operator fun div(num: Double) = Translation2d(x / num, y / num)
    operator fun div(translation2d: Translation2d) = Translation2d(x / translation2d.x, y / translation2d.y)

    fun offsetBy(offset: Double) = Translation2d(x + offset, y + offset)

    fun dot(translation2d: Translation2d) = x * translation2d.x + y * translation2d.y
    fun norm() = hypot(x, y)
    fun distanceTo(translation2d: Translation2d) = hypot(x - translation2d.x, y - translation2d.y)

    fun epsilonEquals(translation2d: Translation2d, epsilon: Double = 1e-6) = abs(x - translation2d.x) <= epsilon && abs(y - translation2d.y) <= epsilon

    fun normalize(): Translation2d {
        val n = norm()
        if (n == 0.0) return zero()

        return this / n
    }

    override fun toString() = "Vector2d(x = $x, y = $y)"
}
