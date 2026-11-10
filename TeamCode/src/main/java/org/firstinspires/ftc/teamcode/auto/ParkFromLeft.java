package org.firstinspires.ftc.teamcode.auto;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.ivy.Command;
import static com.pedropathing.ivy.groups.Groups.sequential;
import com.pedropathing.paths.Path;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import org.firstinspires.ftc.teamcode.LibCommon;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class ParkFromLeft extends OpMode {

    private Follower follower;
    private Servo stinger;
    private int stingerTarget = 0;
    private Servo intakeServo;
    private DcMotor intakeMotor;
    private DcMotor launchWheel;
    private boolean servoIntakeOn = false;
    private boolean motorIntakeOn = false;
    private boolean launcherOn = false;
    private Servo rampServoL;
    private Servo rampServoR;
    private int rampTarget = 0;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final int[][] actions = new int[][]{
            {8, 58, 180, LibCommon.Actions.INIT.ordinal()},
            {27, 54, 180, -1},
            {20, 12, 180, -1},
    };

    private Pose[] poses;
    private int poseIndex = 0;
    private Path park() {
        return line(poses[poseIndex], poses[poseIndex + 1]).linear(poses[poseIndex], poses[poseIndex++ + 1]);
    }

    @Override
    public void init() {
        stinger = hardwareMap.get(Servo.class, "stinger");
        rampServoL = hardwareMap.get(Servo.class, "rampServoL");
        rampServoR = hardwareMap.get(Servo.class, "rampServoR");
        poses = new Pose[actions.length];
        for (int i = 0; i < actions.length; i++){
            poses[i] = poseFactory.of(actions[i][0], actions[i][1], actions[i][2]);
        }
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(poses[0]);
        follower.update();
        stinger.setPosition(stingerTarget);
        rampServoL.setPosition(1.0f - rampTarget);
        rampServoR.setPosition(rampTarget);
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
        schedule(autoRoutine());
    }

    @Override
    public void loop() {
//        if (!follower.following() && poseIndex != poses.length){
//            Scheduler.schedule(follow(follower, park()));
//        }
        telemetry.addData("completion: ", follower.completion());
        follower.update();
        Scheduler.execute();
    }
}