package top.symple.sympleplanner.trajectory

import top.symple.sympleplanner.geometry.Pose2d
import top.symple.sympleplanner.interfaces.IDriveTrain
import top.symple.sympleplanner.interfaces.ILocalizer
import top.symple.sympleplanner.utils.mecanum.fieldToRobotRelative
import top.symple.sympleplanner.utils.mecanum.mecanumWheelSpeeds

abstract class TrajectoryManager(val name: String) {
    var trajectory: List<TrajectoryState> = emptyList()
        private set;
    var headIdx: Int = 0;

    init {
        initialize();
    }

    fun execute(localizer: ILocalizer, driveTrain: IDriveTrain) {
        val currentPos = localizer.getPose();

        if (trajectory.isEmpty()) {
            throw RuntimeException("Trajectory is not initialized");
        }

        val currentTrajectoryState =  trajectory[headIdx];

        val wheelSpeeds = process(currentPos, currentTrajectoryState);

        driveTrain.setPower(wheelSpeeds)

        if (true) { // Todo fix iteration logic.
            headIdx += 1;
        }
    }



    open fun process(pos: Pose2d, trajectoryState: TrajectoryState): DoubleArray{
        val desiredPos = trajectoryState.pose2d ?: return DoubleArray(4);

        val errorX = desiredPos.position.x - pos.position.x;
        val errorY = desiredPos.position.y - pos.position.y;
        val errorTheta = desiredPos.heading.toRadians() - pos.heading.toRadians();


        val velocityX = 1 * errorX;
        val velocityY = 1 * errorY;
        val velocityTheta = 1 * errorTheta;

        val (robotVx, robotVy) = fieldToRobotRelative(
            velocityX, velocityY, pos.heading.toRadians()
        )

        val wheelSpeeds = mecanumWheelSpeeds(robotVx, robotVy, velocityTheta)

        return wheelSpeeds;
    }


    /**
     * This is the initialize method for this class.
     * Call this to initialize the trajectory
     */
    fun initialize() {
        this.trajectory = setTrajectory();
    }

    /**
     * Returns the trajectory states defined by the user.
     */
    protected abstract fun setTrajectory(): List<TrajectoryState>;
}