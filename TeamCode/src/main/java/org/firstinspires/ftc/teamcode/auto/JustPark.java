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

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class JustPark extends OpMode {

    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();

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
        poses = new Pose[]{
                poseFactory.of(2, 54, 180),
                poseFactory.of(27, 54, 180),
                poseFactory.of(27, 30, 0),
                poseFactory.of(27, 2, 0)
        };
//        poses.add(poseFactory.of(2, 54, 180));
//        poses.add(poseFactory.of(42, 54, 0));
//        poses.add(poseFactory.of(27, 16, 0));
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
    }

    @Override
    public void loop() {
//        if (!follower.following() && poseIndex != poses.length){
//            Scheduler.schedule(follow(follower, park()));
//        }
        follower.update();
        Scheduler.execute();
    }
}