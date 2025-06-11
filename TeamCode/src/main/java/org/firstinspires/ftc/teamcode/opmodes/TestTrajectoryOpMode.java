package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.trajectories.TestTrajectory;
import org.firstinspires.ftc.teamcode.chassis.MecanumChassis;

import top.symple.sympleplanner.trajectory.TrajectoryManager;

@TeleOp(name = "Two States Trajectory Test", group = "sympleplanner")
public class TestTrajectoryOpMode extends CommandOpModeEx {
    private MecanumChassis mecanumChassis;
    private TrajectoryManager trajectory;

    @Override
    public void initialize() {
        mecanumChassis = new MecanumChassis(hardwareMap, telemetry);
        trajectory = new TestTrajectory();
    }

    @Override
    public void preRunLoop() {
        this.mecanumChassis.followTrajectory(trajectory).schedule();
    }
}
