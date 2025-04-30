package top.symple.sympleplanner.geometry

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

data class Rotation2d(val cosVal: Double, val sinVal: Double) {
    companion object {
        fun zero() = Rotation2d(1.0, 0.0)
        fun fromRadians(theta: Double) = Rotation2d(cos(theta), sin(theta))
        fun fromDegrees(theta: Double) = Rotation2d(cos(Math.toRadians(theta)), sin(Math.toRadians(theta)))
    }

    operator fun plus(rotation: Rotation2d) = this * rotation
    operator fun minus(rotation: Rotation2d) = this * rotation.inverse()

    operator fun times(vec2d: Translation2d) = Translation2d((cosVal * vec2d.x) - (sinVal * vec2d.y), (sinVal * vec2d.x) + (cosVal * vec2d.y))
    operator fun times(rotation: Rotation2d) = Rotation2d((cosVal * rotation.cosVal) - (sinVal * rotation.sinVal), (cosVal * rotation.sinVal) + (sinVal * rotation.cosVal))

    fun inverse() = fromRadians(-toRadians())

    fun norm() = hypot(cosVal, sinVal)
    fun normalize(): Rotation2d {
        val n = norm()
        if (n == 0.0) return zero()

        return Rotation2d(cosVal / n, sinVal / n)
    }

    fun asTranslation2d() = Translation2d(cosVal, sinVal)
    fun toRadians() = atan2(sinVal, cosVal)
    fun toDegrees() = Math.toDegrees(toRadians())

    override fun toString() = "Rotation2d(cosVal = $cosVal, sinVal = $sinVal, radians = ${toRadians()} (${toDegrees()}°))"
}