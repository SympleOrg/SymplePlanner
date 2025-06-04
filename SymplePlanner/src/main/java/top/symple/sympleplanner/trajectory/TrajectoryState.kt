package top.symple.sympleplanner.trajectory

import top.symple.sympleplanner.geometry.Pose2d
import top.symple.sympleplanner.interfaces.IAction

data class TrajectoryState(val pose2d: Pose2d?, val action: IAction?, val shouldRunActionWhileMoving: Boolean, val desiredRobotVelocityMPS: Double?) {
    constructor(action: IAction, shouldRunActionWhileMoving: Boolean) : this(null, action, shouldRunActionWhileMoving, null)
    constructor(pose2d: Pose2d) : this(pose2d, null, true, null)
    constructor(pose2d: Pose2d, desiredRobotVelocityMPS: Double) : this(pose2d, null, true, desiredRobotVelocityMPS)
}
