package org.firstinspires.ftc.teamcode.localizer;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.bosch.BNO055IMUNew;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.jetbrains.annotations.NotNull;

import top.symple.sympleplanner.geometry.Pose2d;
import top.symple.sympleplanner.geometry.Rotation2d;
import top.symple.sympleplanner.geometry.Translation2d;
import top.symple.sympleplanner.interfaces.ILocalizer;

public class TwoDeadWheelWithGyroLocalizer implements ILocalizer {
    public static class Constants {
        public static final String FORWARD_ENCODER_ID = "dt_forward_encoder";
        public static final String LATERAL_ENCODER_ID = "dt_lateral_encoder";
        public static final String IMU_ID = "imu";

        public static final RevHubOrientationOnRobot.LogoFacingDirection LOGO_FACING_DIRECTION = RevHubOrientationOnRobot.LogoFacingDirection.LEFT;
        public static final RevHubOrientationOnRobot.UsbFacingDirection USB_FACING_DIRECTION = RevHubOrientationOnRobot.UsbFacingDirection.UP;
        public static final ImuType IMU_TYPE = ImuType.BHI260;

        public static final double LATERAL_OFFSET_METER = 0.2; // in meters
        public static final double WHEEL_RADIUS_METER = 0.025; // in meters

        public static final double GEAR_RATIO = 1;
        public static final double TICKS_PER_REV = 2000;

        public enum ImuType {
            BNO055,
            BHI260
        }
    }

    private final Motor.Encoder forwardEncoder;
    private final Motor.Encoder lateralEncode;
    private final IMU imu;

    private final double startingHeading;

    private int lastForwardEncoderPos;
    private int lastLateralEncoderPos;
    private double lastHeading;

    private Pose2d currentPose;

    public TwoDeadWheelWithGyroLocalizer(HardwareMap hardwareMap, Pose2d initPose) {
        this.forwardEncoder = hardwareMap.get(MotorEx.class, Constants.FORWARD_ENCODER_ID).encoder;
        this.lateralEncode = hardwareMap.get(MotorEx.class, Constants.LATERAL_ENCODER_ID).encoder;
        this.imu = hardwareMap.get(Constants.IMU_TYPE == Constants.ImuType.BHI260 ? BHI260IMU.class : BNO055IMUNew.class, Constants.IMU_ID);
        this.imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(Constants.LOGO_FACING_DIRECTION, Constants.USB_FACING_DIRECTION)));
        this.currentPose = initPose;

        this.startingHeading = this.getImuRotation();

        this.lastForwardEncoderPos = this.forwardEncoder.getPosition();
        this.lastLateralEncoderPos = this.lateralEncode.getPosition();
        this.lastHeading = this.getImuRotation();
    }

    @Override
    public @NotNull Pose2d getPose() {
        return this.currentPose;
    }

    @Override
    public void update() {
        double deltaForward = ticksToMeters(this.forwardEncoder.getPosition() - this.lastForwardEncoderPos);
        double deltaLateral = ticksToMeters(this.lateralEncode.getPosition() - this.lastLateralEncoderPos);
        double deltaHeading = this.getImuRotation() - lastHeading;

        double deltaY = deltaForward;
        double deltaX = deltaLateral - Constants.LATERAL_OFFSET_METER * deltaHeading;

        Translation2d deltaRobotPos = Translation2d.from(deltaX, deltaY);
        Translation2d deltaFieldPos = deltaRobotPos.times(this.currentPose.getHeading());

        this.currentPose = Pose2d.from(
                this.currentPose.getPosition().plus(deltaFieldPos),
                Rotation2d.fromRadians(this.getImuRotation() - this.startingHeading) // more accurate
        );

        this.lastForwardEncoderPos = this.forwardEncoder.getPosition();
        this.lastLateralEncoderPos = this.lateralEncode.getPosition();
        this.lastHeading = this.getImuRotation();
    }

    private double getImuRotation() {
        return this.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    private double ticksToMeters(double ticks) {
        return (ticks / Constants.TICKS_PER_REV) * 2 * Math.PI * Constants.WHEEL_RADIUS_METER * Constants.GEAR_RATIO;
    }
}
