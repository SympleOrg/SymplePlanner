package top.symple.sympleplanner.geometry

import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

data class Vector2d(val x: Double, val y: Double) {
    companion object {
        fun zero() = Vector2d(0.0, 0.0)

        fun from(x: Double, y: Double) = Vector2d(x, y)
        fun fromPolar(radius: Double, theta: Double) = Vector2d(radius * cos(theta), radius * sin(theta))
        fun fromPolar(radius: Double, rotation2d: Rotation2d) = Vector2d(radius * rotation2d.x, radius * rotation2d.y)
    }

    operator fun plus(vec2d: Vector2d) = Vector2d(x + vec2d.x, y + vec2d.y)

    operator fun minus(vec2d: Vector2d) = Vector2d(x - vec2d.x, y - vec2d.y)

    operator fun unaryMinus() = Vector2d(-x, -y)

    operator fun times(num: Double) = Vector2d(x * num, y * num)
    operator fun times(vec2d: Vector2d) = Vector2d(x * vec2d.x, y * vec2d.y)
    operator fun div(num: Double) = Vector2d(x / num, y / num)
    operator fun div(vec2d: Vector2d) = Vector2d(x / vec2d.x, y / vec2d.y)

    fun offsetBy(offset: Double) = Vector2d(x + offset, y + offset)

    fun dot(vec2d: Vector2d) = x * vec2d.x + y * vec2d.y
    fun norm() = hypot(x, y)
    fun distanceTo(vec2d: Vector2d) = hypot(x - vec2d.x, y - vec2d.y)

    fun normalize(): Vector2d {
        val n = norm()
        if (n == 0.0) return zero()

        return this / n
    }

    override fun toString() = "Vector2d(x = $x, y = $y)"
}
