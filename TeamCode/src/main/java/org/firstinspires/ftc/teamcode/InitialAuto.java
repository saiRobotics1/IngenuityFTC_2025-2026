package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "InitialAuto")
public class InitialAuto extends LinearOpMode {

    @Override
    public void runOpMode() {
        PanelsTelemetry.INSTANCE.getTelemetry().addLine("Waiting for start...");
        PanelsTelemetry.INSTANCE.getTelemetry().update();

        waitForStart();

        while (opModeIsActive()) {
            // Send to the Panels web dashboard (port 8001)
            PanelsTelemetry.INSTANCE.getTelemetry().addLine("Telemetry is working");
            PanelsTelemetry.INSTANCE.getTelemetry().update();
        }
    }
}
