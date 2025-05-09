package top.symple.sympleplanner.geometry

data class Pose2d(val position: Translation2d, val heading: Rotation2d) {
    companion object {
        fun zero() = Pose2d(Translation2d.zero(), Rotation2d.zero())

        fun from(position: Translation2d, rotation2d: Rotation2d) = Pose2d(position, rotation2d)
        fun from(x: Double, y: Double, rotation2d: Rotation2d) = Pose2d(Translation2d(x, y), rotation2d)
        fun fromRadians(position: Translation2d, radians: Double) = Pose2d(position, Rotation2d.fromRadians(radians))
        fun fromRadians(x: Double, y: Double, radians: Double) = Pose2d(Translation2d(x, y), Rotation2d.fromRadians(radians))
        fun fromDegrees(position: Translation2d, degrees: Double) = Pose2d(position, Rotation2d.fromDegrees(degrees))
        fun fromDegrees(x: Double, y: Double, degrees: Double) = Pose2d(Translation2d(x, y), Rotation2d.fromDegrees(degrees))
    }

    operator fun plus(pose2d: Pose2d) = this.transformBy(pose2d)
    operator fun minus(pose2d: Pose2d) = this.inverse().transformBy(pose2d)

    fun transformBy(pose2d: Pose2d) = Pose2d(heading * pose2d.position + position, heading * pose2d.heading)
    fun transformBy(translation2d: Translation2d) = Pose2d(heading * translation2d + position, heading)

    fun inverse(): Pose2d {
        val invHeading = heading.inverse();
        return Pose2d(invHeading * -position, invHeading)
    }

    fun epsilonEquals(pose2d: Pose2d, epsilon: Double = 1e-6) = position.epsilonEquals(pose2d.position, epsilon) && heading.epsilonEquals(pose2d.heading, epsilon)
    fun normalize() = Pose2d(position, heading.normalize())

    override fun toString() = "Pose2d(pos = $position, heading = $heading)"
}
