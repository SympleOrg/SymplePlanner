package top.symple.sympleplanner.trajectory

import top.symple.sympleplanner.geometry.Pose2d
import top.symple.sympleplanner.interfaces.IDriveTrain
import top.symple.sympleplanner.interfaces.ILocalizer
import top.symple.sympleplanner.utils.mecanum.fieldToRobotRelativeVelocity
import top.symple.sympleplanner.utils.mecanum.mecanumWheelSpeeds

abstract class TrajectoryManager(val name: String) {
    var trajectory: List<TrajectoryState> = emptyList()
        private set;
    private var headIdx: Int = 0;

    init {
        initialize();
    }

    /**
     * This is the initialize method for this class.
     * Call this to initialize the trajectory
     */
    fun initialize() {
        this.trajectory = setTrajectory();
    }

    fun execute(localizer: ILocalizer, driveTrain: IDriveTrain) {
        val currentPos = localizer.getPose();

        if (trajectory.isEmpty()) {
            throw RuntimeException("Trajectory is not initialized");
        }

        if (trajectory.size == headIdx) {
            // This is the end of the trajectory
            // TODO: better
            driveTrain.setPower(DoubleArray(4))
            return;
        }

        val currentTrajectoryState = trajectory[headIdx];

        val wheelSpeeds = process(currentPos, currentTrajectoryState);

        driveTrain.setPower(wheelSpeeds)

        // TODO: add tolerance constant
        if (currentTrajectoryState.isAtPosition(currentPos.position, 0.01)) {
            headIdx += 1;
        }
    }

    protected open fun process(pos: Pose2d, trajectoryState: TrajectoryState): DoubleArray {
        val desiredPos = trajectoryState.pose2d ?: return DoubleArray(4);

        val errorX = desiredPos.position.x - pos.position.x;
        val errorY = desiredPos.position.y - pos.position.y;
        val errorTheta = desiredPos.heading.toRadians() - pos.heading.toRadians();


        // TODO: Create PIDF class and put it here
        // the '1's are the Kp, this is temporary!
        val velocityX = 1 * errorX;
        val velocityY = 1 * errorY;
        val velocityTheta = 1 * errorTheta;

        val (robotVx, robotVy) = fieldToRobotRelativeVelocity(
            velocityX, velocityY, pos.heading.toRadians()
        )

        val wheelSpeeds = mecanumWheelSpeeds(robotVx, robotVy, velocityTheta)

        return wheelSpeeds;
    }

    /**
     * Returns the trajectory states defined by the user.
     */
    protected abstract fun setTrajectory(): List<TrajectoryState>;
}