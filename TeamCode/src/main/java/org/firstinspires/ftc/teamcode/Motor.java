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
public class Motor extends LinearOpMode
{
    private SparkFunOTOS otos;

    public void runOpMode()
    {
        //setting PID Controllers

        //method I made below that initialized hardware
        initializeHardware();

        waitForStart(); //FTC SDK method which wait for the "Play"

        // MAIN PART OF THE CODE!!!!!!!!!!!!
        while (opModeIsActive())
        {
            otos.calibrateImu();
            telemetry.addData("otospos_x",otos.getPosition());
            telemetry.update();
        }
    }


    // Method to stop all motors

    private void initializeHardware()
    {
        // Hardware initialization

        otos = hardwareMap.get(SparkFunOTOS.class,"otos");
        otos.setLinearUnit(DistanceUnit.INCH);     // choose your units
        otos.setAngularUnit(AngleUnit.DEGREES);

        boolean connected = otos.begin();          // check presence
        boolean imuOk     = otos.calibrateImu();   // ~0.6 s for full (still robot)
        otos.resetTracking();                      // zero pose at start

        telemetry.addData("OTOS connected", connected);
        telemetry.addData("IMU calibrated", imuOk);
        telemetry.update();
    }

}
