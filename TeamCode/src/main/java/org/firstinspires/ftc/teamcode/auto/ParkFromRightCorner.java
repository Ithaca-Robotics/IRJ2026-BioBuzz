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
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class ParkFromRightCorner extends OpMode {

    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final int[][] actions = new int[][]{
            {142, 3, 0, -1},
            {130, 7, 0, -1},
            {100, 7, 180, -1},
            {15, 2, 180, -1},
    };

    // our poses
    private Pose[] poses;
    private int poseIndex = 0;
    private Path park() {
        return line(poses[poseIndex], poses[poseIndex + 1]).linear(poses[poseIndex], poses[poseIndex++ + 1]);
    }

    @Override
    public void init() {
        poses = new Pose[actions.length];
        for (int i = 0; i < actions.length; i++){
            poses[i] = poseFactory.of(actions[i][0], actions[i][1], actions[i][2]);
        }
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
        schedule(autoRoutine());
    }

    @Override
    public void loop() {
        telemetry.addData("completion: ", follower.completion());
        follower.update();
        Scheduler.execute();
    }
}