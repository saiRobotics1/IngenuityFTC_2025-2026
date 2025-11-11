package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode; //utilized linear mode
import com.qualcomm.robotcore.eventloop.opmode.Autonomous; //send to Autonomous on Driver Hub
import com.qualcomm.robotcore.hardware.DcMotor; //use DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple; //
import com.qualcomm.robotcore.util.ElapsedTime; // use for time
import com.qualcomm.robotcore.util.Range; //used to clip
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "OTOS", group = "Draft")
public class OTOS extends LinearOpMode {

    private SparkFunOTOS otos;

    @Override
    public void runOpMode() throws InterruptedException {

        initializeHardware();

        waitForStart();

        while (opModeIsActive()) {

            SparkFunOTOS.Pose2D pos = otos.getPosition();

            telemetry.addData("X (in)", pos.x);
            telemetry.addData("Y (in)", pos.y);
            telemetry.addData("Heading (deg)", pos.h);
            telemetry.update();
        }
    }

    private void initializeHardware() {

        otos = hardwareMap.get(SparkFunOTOS.class,"otos");
        otos.setLinearUnit(DistanceUnit.INCH);
        otos.setAngularUnit(AngleUnit.DEGREES);

        boolean connected = otos.begin();
        if (!connected) {
            telemetry.addLine("OTOS not connected!");
            telemetry.update();
            sleep(3000);
            return;
        }

        // Only do this ONCE while robot is still
        boolean imuOk = otos.calibrateImu();
        otos.resetTracking();   // start odometry at (0,0,0)

        telemetry.addData("OTOS connected", connected);
        telemetry.addData("IMU calibrated", imuOk);
        telemetry.update();
    }
}