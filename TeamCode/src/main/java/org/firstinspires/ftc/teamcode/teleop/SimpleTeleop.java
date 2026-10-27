package org.firstinspires.ftc.teamcode.teleop;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;

@TeleOp
public class SimpleTeleop extends OpMode {

    private Follower follower;
    private Servo stinger;
    private int stingerTarget = 0;
    private Servo intakeServo;
    private DcMotor intakeMotor;
    private DcMotor launchWheel;
    private boolean servoIntakeOn = false;
    private boolean motorIntakeOn = false;
    private boolean launcherOn = false;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose starterPose = poseFactory.of(0, 0, 180);
    // our poses
    private Pose[] poses;
    //    private final ArrayList<Pose> poses = new ArrayList<Pose>();
    private int poseIndex = 0;

    private Path park() {
        return line(poses[poseIndex], poses[poseIndex + 1]).linear(poses[poseIndex], poses[poseIndex++ + 1]);
    }

    @Override
    public void init() {
        // Create a follower for PedroPathing
        follower = Constants.create(hardwareMap);
        follower.setPose(starterPose);

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
        if (gamepad2.aWasReleased()){
            if (stingerTarget == 0) stingerTarget = 1;
            else stingerTarget = 0;
        }
        // Non-continuous servo positions must be set every loop
        stinger.setPosition(stingerTarget);

        // Enable / disable the intake motor by pressing 'x'
        if (gamepad2.xWasReleased()){
            motorIntakeOn = !motorIntakeOn;
            if (motorIntakeOn){
                // Continuous motors just need the power set once
                intakeMotor.setPower(1);
            }else{
                intakeMotor.setPower(0);
            }
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