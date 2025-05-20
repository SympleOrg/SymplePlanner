package top.symple.sympleplanner.geometry

import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

data class Rotation2d(val cosVal: Double, val sinVal: Double) {
    companion object {
        @JvmStatic fun zero() = Rotation2d(1.0, 0.0)
        @JvmStatic fun fromRadians(radians: Double) = Rotation2d(cos(radians), sin(radians))
        @JvmStatic fun fromDegrees(degrees: Double) = Rotation2d(cos(Math.toRadians(degrees)), sin(Math.toRadians(degrees)))
    }

    operator fun plus(rotation2d: Rotation2d) = this * rotation2d
    operator fun minus(rotation2d: Rotation2d) = this * rotation2d.inverse()

    operator fun times(translation2d: Translation2d) = Rotation2d((cosVal * translation2d.x) - (sinVal * translation2d.y), (sinVal * translation2d.x) + (cosVal * translation2d.y))
    operator fun times(rotation2d: Rotation2d) = Rotation2d((cosVal * rotation2d.cosVal) - (sinVal * rotation2d.sinVal), (cosVal * rotation2d.sinVal) + (sinVal * rotation2d.cosVal))

    fun inverse() = Rotation2d.fromRadians(-toRadians())

    fun norm() = hypot(cosVal, sinVal)
    fun normalize(): Rotation2d {
        val n = norm()
        if (n == 0.0) return zero()

        return Rotation2d(cosVal / n, sinVal / n)
    }

    fun asTranslation2d() = Translation2d(cosVal, sinVal)
    fun toRadians() = atan2(sinVal, cosVal)
    fun toDegrees() = Math.toDegrees(toRadians())
    fun epsilonEquals(rotation2d: Rotation2d, epsilon: Double = 1e-6) = abs(cosVal - rotation2d.cosVal) <= epsilon && abs(sinVal - rotation2d.sinVal) <= epsilon

    override fun toString() = "Rotation2d(cosVal = $cosVal, sinVal = $sinVal, radians = ${toRadians()} (${toDegrees()}°))"
}