package org.firstinspires.ftc.teamcode.chassis;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import top.symple.sympleplanner.interfaces.IDriveTrain;

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

    public MecanumChassis(HardwareMap hardwareMap) {
        this.frontLeftMotor = new MotorEx(hardwareMap, Constants.FRONT_LEFT_MOTOR_ID);
        this.frontRightMotor = new MotorEx(hardwareMap, Constants.FRONT_RIGHT_MOTOR_ID);
        this.backLeftMotor = new MotorEx(hardwareMap, Constants.BACK_LEFT_MOTOR_ID);
        this.backRightMotor = new MotorEx(hardwareMap, Constants.BACK_RIGHT_MOTOR_ID);
    }

    @Override
    public void setPower(@NonNull int[] power) {
        this.frontLeftMotor.set(power[0]);
        this.frontRightMotor.set(power[1]);
        this.backLeftMotor.set(power[2]);
        this.backRightMotor.set(power[3]);
    }
}
