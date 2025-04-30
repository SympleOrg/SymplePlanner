package top.symple.sympleplanner.geometry

data class Pose2d(val pos: Translation2d, val heading: Rotation2d) {
    companion object {
        fun zero() = Pose2d(Translation2d.zero(), Rotation2d.zero())

        fun fromRadians(pos: Translation2d, radians: Double) = Pose2d(pos, Rotation2d.fromRadians(radians))
        fun fromRadians(x: Double, y: Double, radians: Double) = Pose2d(Translation2d(x, y), Rotation2d.fromRadians(radians))
        fun fromDegrees(pos: Translation2d, degrees: Double) = Pose2d(pos, Rotation2d.fromDegrees(degrees))
        fun fromDegrees(x: Double, y: Double, degrees: Double) = Pose2d(Translation2d(x, y), Rotation2d.fromDegrees(degrees))
    }

    operator fun plus(pose2d: Pose2d) = this.transformBy(pose2d)
    operator fun minus(pose2d: Pose2d) = this.inverse().transformBy(pose2d)

    fun transformBy(pose2d: Pose2d) = Pose2d(heading * pose2d.pos + pos, heading * pose2d.heading)
    fun transformBy(vec2d: Translation2d) = heading * vec2d + pos

    fun inverse() = Pose2d(heading.inverse() * -pos, heading.inverse())

    fun epsilonEquals(pose2d: Pose2d, epsilon: Double = 1e-6) = pos.epsilonEquals(pose2d.pos, epsilon) && heading.epsilonEquals(pose2d.heading, epsilon)
    fun normalize() = Pose2d(pos, heading.normalize())

    override fun toString() = "Pose2d(pos = $pos, heading = $heading)"
}
