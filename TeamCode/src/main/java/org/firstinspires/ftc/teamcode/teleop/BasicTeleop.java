package org.firstinspires.ftc.teamcode.teleop;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class BasicTeleop extends OpMode {

    private Follower follower;
    private Servo stinger;
    private int stingerTarget = 0;
    private Servo rampServoL;
    private Servo rampServoR;
    private int rampTarget = 0;
    private Servo intakeServo;
    private DcMotor intakeMotor;
    private DcMotor launchWheel;
    private boolean servoIntakeOn = false;
    private boolean motorIntakeOn = false;
    private boolean launcherOn = false;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose starterPose = poseFactory.of(0, 0, 180);
    private ElapsedTime timer;
    private double raisedTime;
    private double raisePause = 0.25;

    @Override
    public void init() {
        // Create a follower for PedroPathing
        follower = Constants.create(hardwareMap);
        follower.setPose(starterPose);

        // Basic servo imports
        stinger = hardwareMap.get(Servo.class, "stinger");
        intakeServo = hardwareMap.get(Servo.class, "intakeServo");
        rampServoL = hardwareMap.get(Servo.class, "rampServoL");
        rampServoR = hardwareMap.get(Servo.class, "rampServoR");

        // Import the intake motor (the grey rubber flaps) and set its mode + direction
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Import the launcher wheel and set its mode + direction
        launchWheel = hardwareMap.get(DcMotor.class, "launchWheel");
        launchWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launchWheel.setDirection(DcMotorSimple.Direction.FORWARD);

        stinger.setPosition(stingerTarget);
        rampServoL.setPosition(1.0f - rampTarget);
        rampServoR.setPosition(rampTarget);

        timer = new ElapsedTime();
        raisedTime = timer.time();
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        // Get the controller inputs for driving left and right (left stick) and turning (right stick)
        double forward = -gamepad1.left_stick_y;
        double lateral = gamepad1.left_stick_x;
        double turn = -gamepad1.right_stick_x;

        DrivePowers powers = ManualDrive.fieldCentric(
                lateral,
                forward,
                turn,
                follower.pose().heading()
        );

        // Print out some debug data
        telemetry.addData("Forward: ", forward);
        telemetry.addData("Lateral: ", lateral);
        telemetry.addData("Turn: ", turn);
        Pose robotPose = follower.pose();
        telemetry.addData("Robot X", robotPose.x());
        telemetry.addData("Robot Y", robotPose.y());
        telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading()));
        telemetry.update(); // This actually displays the text on the screen

        // Raise and lower the stinger based on pressing 'a'
        if (gamepad2.aWasReleased() && rampTarget == 0){
            if (stingerTarget == 0 && (timer.time() - raisedTime) > raisePause){
                stingerTarget = 1;
            }else if (stingerTarget == 1){
                raisedTime = timer.time();
                stingerTarget = 0;
            }
        }
        // Non-continuous servo positions must be set every loop
        stinger.setPosition(stingerTarget);

        if (gamepad2.dpadDownWasReleased() && stingerTarget == 0){
            if (rampTarget == 0 && (timer.time() - raisedTime) > raisePause){
                rampTarget = 1;
            }else if (rampTarget == 1){
                raisedTime = timer.time();
                rampTarget = 0;
            }
        }
        rampServoL.setPosition(1.0f - rampTarget);
        rampServoR.setPosition(rampTarget);

        // Enable / disable the intake motor by pressing 'x'
        if (gamepad2.xWasReleased()){
            motorIntakeOn = !motorIntakeOn;
        }
        if (motorIntakeOn && gamepad2.right_bumper){
            intakeMotor.setPower(-1);
        }else if (motorIntakeOn){
            intakeMotor.setPower(1);
        }else{
            intakeMotor.setPower(0);
        }

        // Enable / disable the intake servo by pressing 'b'
        if (gamepad2.bWasReleased()){
            servoIntakeOn = !servoIntakeOn;
            if (servoIntakeOn){
                // Continuous servos just need the power set once
                intakeServo.setPosition(1);
            }else{
                intakeServo.setPosition(0.5);
            }
        }

        // Enable / disable the launcher by pressing 'y'
        if (gamepad2.yWasReleased()){
            launcherOn = !launcherOn;
            if (launcherOn){
                launchWheel.setPower(0.5f);
            }else{
                launchWheel.setPower(0);
            }
        }

        // PedroPathing movement using joysticks
        follower.manual(powers);
        follower.update();
    }
}