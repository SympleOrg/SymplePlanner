package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.chassis.MecanumChassis;

@TeleOp(name = "Mecanum Chassis Test", group = "sympleplanner")
public class MecanumChassisTestOpMode extends CommandOpMode {
    private MecanumChassis mecanumChassis;

    @Override
    public void initialize() {
        this.mecanumChassis = new MecanumChassis(hardwareMap);
    }
}
