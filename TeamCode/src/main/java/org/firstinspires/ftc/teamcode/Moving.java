package org.firstinspires.ftc.teamcode;

import com.bylazar.field.Line;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.ftc.localization.constants.OTOSConstants;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.robotcore.util.ElapsedTime;

import kotlin.math.*;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

//spark fun initialize
@Autonomous(name = "Moving")
public class Moving extends OpMode {
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    private Path testRun;
    private PathChain pathChain;
    private final PanelsTelemetry panelsTelemetry = PanelsTelemetry.INSTANCE;
    private final Pose startPose = new Pose(0, 0, Math.toRadians(0)); // Start Pose of our robot.
    private final Pose endPose = new Pose(12, 0, Math.toRadians(0)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.


    public void buildPaths() {
        testRun = new Path(new BezierLine(startPose, endPose));
        testRun.setLinearHeadingInterpolation(startPose.getHeading(), endPose.getHeading());

        /*pathChain = follower.pathBuilder()
                .addPath(new BezierLine(startPose, endPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), endPose.getHeading())
                .build();*/

    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(testRun);
                setPathState(1);
                break;

        }
    }

    /**
     * These change the states of the paths and actions. It will also reset the timers of the individual switches
     **/
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
    @Override
    public void loop() {

        // These loop the movements of the robot, these must be called continuously in order to work
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub for debugging
        PanelsTelemetry.INSTANCE.getTelemetry().addData("path state", pathState);
        PanelsTelemetry.INSTANCE.getTelemetry().addData("x", follower.getPose().getX());
        PanelsTelemetry.INSTANCE.getTelemetry().addData("y", follower.getPose().getY());
        PanelsTelemetry.INSTANCE.getTelemetry().addData("heading", follower.getPose().getHeading());
        PanelsTelemetry.INSTANCE.getTelemetry().update();
    }

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();


        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
    }

    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {}

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {}
}
