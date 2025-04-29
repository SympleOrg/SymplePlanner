package top.symple.sympleplanner.geometry

import kotlin.math.hypot

data class Vector2d(val x: Double, val y: Double) {
    companion object {
        fun zero() = Vector2d(0.0, 0.0)
    }

    operator fun plus(vec2d: Vector2d) = Vector2d(x + vec2d.x, y + vec2d.y)
    operator fun plus(num: Double) = Vector2d(x + num, y + num)

    operator fun minus(vec2d: Vector2d) = Vector2d(x - vec2d.x, y - vec2d.y)
    operator fun minus(num: Double) = Vector2d(x - num, y - num)

    operator fun unaryMinus() = Vector2d(-x, -y)

    operator fun times(num: Double) = Vector2d(x * num, y * num)
    operator fun div(num: Double) = Vector2d(x / num, y / num)

    fun norm() = hypot(x, y)
}
