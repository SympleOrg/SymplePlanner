package org.firstinspires.ftc.teamcode.trajectories;

import androidx.annotation.NonNull;

import java.util.List;

import top.symple.sympleplanner.geometry.Pose2d;
import top.symple.sympleplanner.geometry.Rotation2d;
import top.symple.sympleplanner.trajectory.TrajectoryManager;
import top.symple.sympleplanner.trajectory.TrajectoryState;

public class TestTrajectory extends TrajectoryManager {
    public TestTrajectory() {
        super("Test Trajectory");
    }

    @NonNull
    @Override
    protected List<TrajectoryState> setTrajectory() {
        return List.of(
                new TrajectoryState(
                        Pose2d.from(0.5, 0, Rotation2d.zero())
                ),
                new TrajectoryState(
                        Pose2d.from(0.5, 0.5, Rotation2d.zero())
                )
        );
    }
}
