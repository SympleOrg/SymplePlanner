package top.symple.sympleplanner.geometry

import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

data class Translation2d(val x: Double, val y: Double) {
    companion object {
        fun zero() = Translation2d(0.0, 0.0)

        fun from(x: Double, y: Double) = Translation2d(x, y)
        fun fromPolar(radius: Double, theta: Double) = Translation2d(radius * cos(theta), radius * sin(theta))
        fun fromPolar(radius: Double, rotation2d: Rotation2d) = Translation2d(radius * rotation2d.cosVal, radius * rotation2d.sinVal)
    }

    operator fun plus(vec2d: Translation2d) = Translation2d(x + vec2d.x, y + vec2d.y)

    operator fun minus(vec2d: Translation2d) = Translation2d(x - vec2d.x, y - vec2d.y)

    operator fun unaryMinus() = Translation2d(-x, -y)

    operator fun times(num: Double) = Translation2d(x * num, y * num)
    operator fun times(vec2d: Translation2d) = Translation2d(x * vec2d.x, y * vec2d.y)
    operator fun div(num: Double) = Translation2d(x / num, y / num)
    operator fun div(vec2d: Translation2d) = Translation2d(x / vec2d.x, y / vec2d.y)

    fun offsetBy(offset: Double) = Translation2d(x + offset, y + offset)

    fun dot(vec2d: Translation2d) = x * vec2d.x + y * vec2d.y
    fun norm() = hypot(x, y)
    fun distanceTo(vec2d: Translation2d) = hypot(x - vec2d.x, y - vec2d.y)

    fun normalize(): Translation2d {
        val n = norm()
        if (n == 0.0) return zero()

        return this / n
    }

    override fun toString() = "Vector2d(x = $x, y = $y)"
}
