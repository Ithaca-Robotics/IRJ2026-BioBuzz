package org.firstinspires.ftc.teamcode.auto;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class ShootEmptyFlowerThenParkFromRight extends OpMode {

    private Follower follower;
    private Servo stinger;
    private int stingerTarget = 0;
    private Servo rampServoL;
    private Servo rampServoR;
//    private int rampTarget = 0;
    private Servo intakeServo;
    private DcMotor intakeMotor;
    private DcMotor launchWheel;
    private boolean servoIntakeOn = false;
    private boolean motorIntakeOn = false;
    private boolean launcherOn = false;
    private ElapsedTime timer;
    private boolean rampDeployed = false;
    private final int spinupTime = 8000;
    private final int launchTime = 12000;
    private int stage = 0;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final int[][] actions = new int[][]{
            {136, 58, 0},
            {120, 30, 0},
            {96, 24, 270},
            {96, 17, 270},
            {96, 24, 270},
            {48, 12, 180},
    };

    // our poses
    private Pose[] poses;
    private int poseIndex = 0;
    private Path park() {
        return line(poses[poseIndex], poses[poseIndex + 1]).linear(poses[poseIndex], poses[poseIndex++ + 1]);
    }

    private void setRampTarget(float pos){
        rampServoL.setPosition(1.0f - pos);
        rampServoR.setPosition(pos);
    }

    @Override
    public void init() {
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
        poses = new Pose[actions.length];
        for (int i = 0; i < actions.length; i++){
            poses[i] = poseFactory.of(actions[i][0], actions[i][1], actions[i][2]);
        }
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(poses[0]);
        follower.update();
        stinger.setPosition(stingerTarget);
        setRampTarget(0.0f);
    }

    private Command autoRoutine(){
        Command[] commands = new Command[poses.length - 1];
        for (int i = 0; i < poses.length - 1; i++){
            commands[i] = follow(follower, park());
        }
        return sequential(commands);
    }

    @Override
    public void start() {
//        schedule(autoRoutine());
        launcherOn = true;
        launchWheel.setPower(0.5f);
        timer = new ElapsedTime();
    }

    @Override
    public void loop() {
        if (stage == 3 && follower.idle()){
//            if (rampDeployed){
//
//            }else{
//                r`
//            }
//            if (ourNewTimer > 2 seconds){
//                stage++;
//            }
        }
        if (stage == 2 && follower.idle()) {
            setRampTarget(1.0f);
            schedule(follow(follower, park()));
            stage++;
        }else if (stage == 1 && timer.milliseconds() > launchTime + spinupTime){
            servoIntakeOn = false;
            intakeServo.setPosition(0.5);
            launcherOn = false;
            launchWheel.setPower(0);
//            motorIntakeOn = false;
//            intakeMotor.setPower(0);
            schedule(new Command[] {
                    follow(follower, park()),
                    follow(follower, park()),
            });
            stage++;
        }else if (stage == 0 && timer.milliseconds() > spinupTime){
            servoIntakeOn = true;
            intakeServo.setPosition(1);
            motorIntakeOn = true;
            intakeMotor.setPower(1);
            stage++;
        }
        telemetry.addData("completion: ", follower.completion());
        telemetry.addData("Heading: ", follower.pose().heading());
        telemetry.update();

        follower.update();
        Scheduler.execute();
    }
}