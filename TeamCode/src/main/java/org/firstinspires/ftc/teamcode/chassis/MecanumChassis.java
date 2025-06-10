package org.firstinspires.ftc.teamcode.chassis;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.FunctionalCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.localizer.ThreeDeadWheelLocalizer;

import top.symple.sympleplanner.geometry.Pose2d;
import top.symple.sympleplanner.interfaces.IDriveTrain;
import top.symple.sympleplanner.interfaces.ILocalizer;
import top.symple.sympleplanner.trajectory.TrajectoryManager;

public class MecanumChassis extends SubsystemBase implements IDriveTrain {
    public static class Constants {
        public static final String FRONT_LEFT_MOTOR_ID = "dt_front_left";
        public static final String FRONT_RIGHT_MOTOR_ID = "dt_front_right";
        public static final String BACK_LEFT_MOTOR_ID = "dt_back_left";
        public static final String BACK_RIGHT_MOTOR_ID = "dt_back_right";
    }

    private final MotorEx frontLeftMotor;
    private final MotorEx frontRightMotor;
    private final MotorEx backLeftMotor;
    private final MotorEx backRightMotor;

    private final ILocalizer localizer;
    private final Telemetry telemetry;

    public MecanumChassis(HardwareMap hardwareMap, Telemetry telemetry) {
        this.frontLeftMotor = new MotorEx(hardwareMap, Constants.FRONT_LEFT_MOTOR_ID);
        this.frontRightMotor = new MotorEx(hardwareMap, Constants.FRONT_RIGHT_MOTOR_ID);
        this.backLeftMotor = new MotorEx(hardwareMap, Constants.BACK_LEFT_MOTOR_ID);
        this.backRightMotor = new MotorEx(hardwareMap, Constants.BACK_RIGHT_MOTOR_ID);

        this.localizer = new ThreeDeadWheelLocalizer(hardwareMap, Pose2d.zero());

        this.telemetry = telemetry;
    }

    @Override
    public void setPower(@NonNull double[] power) {
        this.frontLeftMotor.set(power[0]);
        this.frontRightMotor.set(power[1]);
        this.backLeftMotor.set(power[2]);
        this.backRightMotor.set(power[3]);
    }

    @Override
    public void periodic() {
        this.localizer.update();
        telemetry.addData("Robot Pos", this.getCurrentPosition().toString());
    }

    public Pose2d getCurrentPosition() {
        return this.localizer.getPose();
    }

    public Command followTrajectory(TrajectoryManager trajectoryManager) {
        return new FunctionalCommand(
                trajectoryManager::initialize,
                () -> trajectoryManager.execute(this.localizer, this),
                (interrupted) -> {
                },
                () -> false,
                this
        );
    }
}
