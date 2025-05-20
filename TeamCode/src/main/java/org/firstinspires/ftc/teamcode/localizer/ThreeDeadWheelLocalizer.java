package org.firstinspires.ftc.teamcode.localizer;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.jetbrains.annotations.NotNull;

import top.symple.sympleplanner.geometry.Pose2d;
import top.symple.sympleplanner.geometry.Rotation2d;
import top.symple.sympleplanner.geometry.Translation2d;
import top.symple.sympleplanner.interfaces.ILocalizer;

public class ThreeDeadWheelLocalizer implements ILocalizer {
    public static class Constants {
        public static final String LEFT_ENCODER_ID = "dt_left_encoder";
        public static final String RIGHT_ENCODER_ID = "dt_right_encoder";
        public static final String LATERAL_ENCODER_ID = "dt_lateral_encoder";

        public static final double TRACK_WIDTH = 0.47; // in meters
        public static final double LATERAL_OFFSET = 0.2; // in meters
    }

    private final Motor.Encoder leftEncoder;
    private final Motor.Encoder rightEncoder;
    private final Motor.Encoder lateralEncode;

    private int lastLeftEncoderPos = 0;
    private int lastRightEncoderPos = 0;
    private int lastLateralEncoderPos = 0;

    private Pose2d currentPose;

    public ThreeDeadWheelLocalizer(HardwareMap hardwareMap, Pose2d initPose) {
        this.leftEncoder = hardwareMap.get(MotorEx.class, Constants.LEFT_ENCODER_ID).encoder;
        this.rightEncoder = hardwareMap.get(MotorEx.class, Constants.RIGHT_ENCODER_ID).encoder;
        this.lateralEncode = hardwareMap.get(MotorEx.class, Constants.LATERAL_ENCODER_ID).encoder;

        this.currentPose = initPose;

        this.lastLeftEncoderPos = this.leftEncoder.getPosition();
        this.lastRightEncoderPos = this.rightEncoder.getPosition();
        this.lastLateralEncoderPos = this.lateralEncode.getPosition();
    }

    @Override
    public @NotNull Pose2d getPose() {
        return this.currentPose;
    }

    @Override
    public void update() {
        int deltaLeft = this.leftEncoder.getPosition() - this.lastLeftEncoderPos;
        int deltaRight = this.rightEncoder.getPosition() - this.lastRightEncoderPos;
        int deltaLateral = this.lateralEncode.getPosition() - this.lastLateralEncoderPos;

        double deltaHeading = (deltaRight - deltaLeft) / Constants.TRACK_WIDTH;
        double deltaY = (deltaLeft + deltaRight) / 2f;
        double deltaX = deltaLateral - Constants.LATERAL_OFFSET * deltaHeading;

        Translation2d deltaRobotPos = Translation2d.from(deltaX, deltaY);
        Translation2d deltaFieldPos = deltaRobotPos.times(this.currentPose.getHeading());

        this.currentPose = Pose2d.from(
                this.currentPose.getPosition().plus(deltaFieldPos),
                this.currentPose.getHeading().plus(Rotation2d.fromRadians(deltaHeading))
        );

        this.lastLeftEncoderPos = this.leftEncoder.getPosition();
        this.lastRightEncoderPos = this.rightEncoder.getPosition();
        this.lastLateralEncoderPos = this.lateralEncode.getPosition();
    }
}
