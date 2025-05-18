package org.firstinspires.ftc.teamcode.chassis;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import top.symple.sympleplanner.interfaces.IDriveTrain;
import top.symple.sympleplanner.util.MotorPowerSet;

public class MecanumChassis extends SubsystemBase implements IDriveTrain {
    private final MotorEx frontLeftMotor;
    private final MotorEx frontRightMotor;
    private final MotorEx backLeftMotor;
    private final MotorEx backRightMotor;

    public MecanumChassis(HardwareMap hardwareMap) {
        this.frontLeftMotor = new MotorEx(hardwareMap, "leg_front_left");
        this.frontRightMotor = new MotorEx(hardwareMap, "leg_front_right");
        this.backLeftMotor = new MotorEx(hardwareMap, "leg_back_left");
        this.backRightMotor = new MotorEx(hardwareMap, "leg_back_right");
    }

    @Override
    public void setPower(@NonNull MotorPowerSet power) {
        this.frontLeftMotor.set(power.getFrontLeft());
        this.frontRightMotor.set(power.getFrontRight());
        this.backLeftMotor.set(power.getBackLeft());
        this.backRightMotor.set(power.getBackRight());
    }
}
