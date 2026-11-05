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

import org.firstinspires.ftc.teamcode.LibCommon;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class ShootThenParkFromLeft extends OpMode {

    private Follower follower;
    private Servo stinger;
    private int stingerTarget = 0;
    private Servo intakeServo;
    private DcMotor intakeMotor;
    private DcMotor launchWheel;
    private boolean servoIntakeOn = false;
    private boolean motorIntakeOn = false;
    private boolean launcherOn = false;
    private ElapsedTime timer;
    private final int waitTime = 12000;
    private final int spinupTime = 3000;
    private final int launchTime = 10000;
    private int stage = 0;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final int[][] actions = new int[][]{
            {2, 51, 180, LibCommon.Actions.INIT.ordinal()},
            {27, 54, 180, -1},
            {15, 2, 180, -1},
    };

    // our poses
    private Pose[] poses;
//    private final ArrayList<Pose> poses = new ArrayList<Pose>();
    private int poseIndex = 0;
//    private final Pose startPose = poseFactory.of(2, 54, 180);
//    private final Pose midPose = poseFactory.of(42, 54, 0);
//    private final Pose park = poseFactory.of(27, 16, 0);
    private Path park() {
        return line(poses[poseIndex], poses[poseIndex + 1]).linear(poses[poseIndex], poses[poseIndex++ + 1]);
    }

    @Override
    public void init() {
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
        poses = new Pose[actions.length];
        for (int i = 0; i < actions.length; i++){
            poses[i] = poseFactory.of(actions[i][0], actions[i][1], actions[i][2]);
        }
//        poses = new Pose[]{
//                poseFactory.of(2, 54, 180),
//                poseFactory.of(27, 54, 180),
//                poseFactory.of(27, 30, 0),
//                poseFactory.of(27, 2, 0)
//        };
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(poses[0]);
        follower.update();
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
//        for (int i = 0; i < poses.length - 1; i++) {
//            schedule(follow(follower, park()));
//        }
        schedule(autoRoutine());
        timer = new ElapsedTime();
    }

    @Override
    public void loop() {
//        if (!follower.following() && poseIndex != poses.length){
//            Scheduler.schedule(follow(follower, park()));
//        }
        if (stage == 2 && timer.milliseconds() > waitTime + spinupTime + launchTime){
            servoIntakeOn = false;
            intakeServo.setPosition(0.5);
            launcherOn = false;
            launchWheel.setPower(0);
            motorIntakeOn = false;
            intakeMotor.setPower(0);
            stage++;
        }else if (stage == 1 && timer.milliseconds() > waitTime + spinupTime){
            servoIntakeOn = true;
            intakeServo.setPosition(1);
            stage++;
        }else if (stage == 0 && timer.milliseconds() > waitTime){
            launcherOn = true;
            launchWheel.setPower(0.5f);
            motorIntakeOn = true;
            intakeMotor.setPower(1);
            stage++;
        }
        telemetry.addData("completion: ", follower.completion());
        if (stage > 2){
            follower.update();
            Scheduler.execute();
        }
    }
}