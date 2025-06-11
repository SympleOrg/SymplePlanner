package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;

public abstract class CommandOpModeEx extends CommandOpMode {
    public void preRunLoop() { }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        preRunLoop(); // Why this is not in the default command op mode, SO USEFUL!

        // run the scheduler
        while (!isStopRequested() && opModeIsActive()) {
            run();
            telemetry.update();
        }
        reset();
    }
}
