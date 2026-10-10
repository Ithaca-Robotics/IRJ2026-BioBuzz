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
        follower = Constants.create(hardwareMap);
        stinger = hardwareMap.get(Servo.class, "stinger");
        intakeServo = hardwareMap.get(Servo.class, "intakeServo");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
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
        double forward = -gamepad1.left_stick_y;
        double lateral = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_stick_x;
        telemetry.addData("Forward: ", forward);
        telemetry.addData("Lateral: ", lateral);
        telemetry.addData("Turn: ", turn);
        telemetry.update();
        if (gamepad1.aWasReleased()){
            if (stingerTarget == 0) stingerTarget = 1;
            else stingerTarget = 0;
        }
        if (gamepad1.xWasReleased()){
            intakeOn = !intakeOn;
            if (intakeOn){
                intakeServo.setPosition(1);
                intakeMotor.setPower(1);
            }else{
                intakeServo.setPosition(0.5);
                intakeMotor.setPower(0);
            }
        }
        if (gamepad1.yWasReleased()){
            launcherOn = !launcherOn;
            if (launcherOn){
                launchWheel.setPower(1);
            }else{
                launchWheel.setPower(0);
            }
        }
        stinger.setPosition(stingerTarget);
        follower.manual(forward, lateral, turn);
        follower.update();
    }
}