package top.symple.sympleplanner.geometry

data class Pose2d(val pos: Vector2d, val heading: Rotation2d) {
    constructor(pos: Vector2d, heading: Double) : this(pos, Rotation2d.fromAngle(heading))
    constructor(x: Double, y: Double, heading: Double) : this(Vector2d(x, y), heading)

    companion object {
        fun zero() = Pose2d(0.0, 0.0, 0.0)
    }

    operator fun plus(pose2d: Pose2d) = this * pose2d
    operator fun minus(pose2d: Pose2d) = this * pose2d

    operator fun times(pose2d: Pose2d) = Pose2d(heading * pose2d.pos + pos, heading * pose2d.heading)
    operator fun times(vec2d: Vector2d) = heading * vec2d + pos

    fun inverse() = Pose2d(heading.inverse() * -pos, heading.inverse())
}
