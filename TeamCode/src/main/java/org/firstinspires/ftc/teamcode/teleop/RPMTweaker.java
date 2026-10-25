package org.firstinspires.ftc.teamcode.teleop;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp
public class RPMTweaker extends OpMode {

    private Follower follower;
    private Servo stinger;
    private int stingerTarget = 0;
    private Servo intakeServo;
    private DcMotor intakeMotor;
    private DcMotor launchWheel;
    private boolean servoIntakeOn = false;
    private boolean motorIntakeOn = false;
    private boolean launcherOn = false;
    private float launcherPower = 1.0f;
    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;

    @Override
    public void init() {
        // Create a follower for PedroPathing
        follower = Constants.create(hardwareMap);

        // Basic servo imports
        stinger = hardwareMap.get(Servo.class, "stinger");
        intakeServo = hardwareMap.get(Servo.class, "intakeServo");

        // Import the intake motor (the grey rubber flaps) and set its mode + direction
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Import the launcher wheel and set its mode + direction
        launchWheel = hardwareMap.get(DcMotor.class, "launchWheel");
        launchWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launchWheel.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void start() {
        stinger.setPosition(stingerTarget);
    }

    @Override
    public void loop() {
        // Get the controller inputs for driving left and right (left stick) and turning (right stick)
        double forward = -gamepad1.left_stick_y;
        double lateral = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_stick_x;

        if (!gamepad1.dpad_up && lastDpadUp){
            launcherPower += 0.1f;
            if (launcherPower > 1) launcherPower = 1.0f;
            if (launcherOn){
                launchWheel.setPower(launcherPower);
            }
        }else if (!gamepad1.dpad_down && lastDpadDown){
            launcherPower -= 0.1f;
            if (launcherPower < 0){
                launcherPower = 0;
            }
            if (launcherOn){
                launchWheel.setPower(launcherPower);
            }
        }
        lastDpadUp = gamepad1.dpad_up;
        lastDpadDown = gamepad1.dpad_down;

        // Raise and lower the stinger based on pressing 'a'
        if (gamepad1.aWasReleased()){
            if (stingerTarget == 0) stingerTarget = 1;
            else stingerTarget = 0;
        }
        // Non-continuous servo positions must be set every loop
        stinger.setPosition(stingerTarget);

        // Enable / disable the intake motor by pressing 'x'
        if (gamepad1.xWasReleased()){
            motorIntakeOn = !motorIntakeOn;
            if (motorIntakeOn){
                // Continuous motors just need the power set once
                intakeMotor.setPower(1);
            }else{
                intakeMotor.setPower(0);
            }
        }

        // Enable / disable the intake servo by pressing 'b'
        if (gamepad1.bWasReleased()){
            servoIntakeOn = !servoIntakeOn;
            if (servoIntakeOn){
                // Continuous servos just need the power set once
                intakeServo.setPosition(1);
            }else{
                intakeServo.setPosition(0.5);
            }
        }

        // Enable / disable the launcher by pressing 'y'
        if (gamepad1.yWasReleased()){
            launcherOn = !launcherOn;
            if (launcherOn){
                launchWheel.setPower(1);
            }else{
                launchWheel.setPower(0);
            }
        }

        // Print out some debug data
        telemetry.addData("Forward: ", forward);
        telemetry.addData("Lateral: ", lateral);
        telemetry.addData("Turn: ", turn);
        telemetry.addData("LauncherPower: ", launcherPower);
        telemetry.update(); // This actually displays the text on the screen

        // PedroPathing movement using joysticks
        follower.manual(forward, lateral, turn);
        follower.update();
    }
}