package top.symple.sympleplanner.geometry

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

data class Rotation2d(val x: Double, val y: Double) {
    companion object {
        fun zero() = Rotation2d(0.0, 0.0)
        fun fromAngle(theta: Double) = Rotation2d(cos(theta), sin(theta))
    }

    operator fun plus(angle: Double) = this * fromAngle(angle)
    operator fun plus(rotation: Rotation2d) = this * rotation

    operator fun minus(angle: Double) = this - fromAngle(angle)
    operator fun minus(rotation: Rotation2d) = (rotation.inverse() * this)

    operator fun times(vec2d: Translation2d) = Translation2d((x * vec2d.x) - (y * vec2d.y), (y * vec2d.x) + (x * vec2d.y))
    operator fun times(rotation: Rotation2d) = Rotation2d((x * rotation.x) - (y * rotation.y), (x * rotation.y) + (y * rotation.x))

    fun toVector() = Translation2d(x, y)
    fun inverse() = Rotation2d(x, -y)

    fun toAngle() = atan2(y, x)
}