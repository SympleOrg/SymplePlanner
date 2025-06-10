package top.symple.sympleplanner.trajectory

import top.symple.sympleplanner.geometry.Pose2d
import top.symple.sympleplanner.geometry.Translation2d
import top.symple.sympleplanner.interfaces.IAction

data class TrajectoryState(val pose2d: Pose2d?, val action: IAction?, @get:JvmName("shouldRunActionWhileMoving") val shouldRunActionWhileMoving: Boolean, val desiredRobotVelocityMPS: Double?) {
    constructor(action: IAction, shouldRunActionWhileMoving: Boolean) : this(null, action, shouldRunActionWhileMoving, null)
    constructor(pose2d: Pose2d) : this(pose2d, null, true, null)
    constructor(pose2d: Pose2d, desiredRobotVelocityMPS: Double) : this(pose2d, null, true, desiredRobotVelocityMPS)

    fun isNearState(currentPosition: Translation2d, tolerance: Double = 0.01): Boolean {
        return this.pose2d == null
                || this.pose2d.position.distanceTo(currentPosition) <= tolerance;
    }
}
