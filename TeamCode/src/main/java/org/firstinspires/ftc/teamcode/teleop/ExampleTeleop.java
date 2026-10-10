package org.firstinspires.ftc.teamcode.teleop;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp
public class ExampleTeleop extends OpMode {

    private Follower follower;
    private Servo stinger;
    private int stingerTarget = 0;
    private Servo intakeServo;
    private DcMotor intakeMotor;
    private DcMotor launchWheel;
    private boolean intakeOn = false;
    private boolean launcherOn = false;

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

        // Print out some debug data
        telemetry.addData("Forward: ", forward);
        telemetry.addData("Lateral: ", lateral);
        telemetry.addData("Turn: ", turn);
        telemetry.update(); // This actually displays the text on the screen

        // Raise and lower the stinger based on pressing 'a'
        if (gamepad1.aWasReleased()){
            if (stingerTarget == 0) stingerTarget = 1;
            else stingerTarget = 0;
        }
        // Non-continuous servo positions must be set every loop
        stinger.setPosition(stingerTarget);

        // Enable / disable the intake by pressing 'x'
        if (gamepad1.xWasReleased()){
            intakeOn = !intakeOn;
            if (intakeOn){
                // Continuous servos and motors just need the power set once
                intakeServo.setPosition(1);
                intakeMotor.setPower(1);
            }else{
                intakeServo.setPosition(0.5);
                intakeMotor.setPower(0);
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

        // PedroPathing movement using joysticks
        follower.manual(forward, lateral, turn);
        follower.update();
    }
}