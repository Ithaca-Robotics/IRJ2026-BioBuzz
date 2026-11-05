package org.firstinspires.ftc.teamcode.lib;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.lib.actions.SimpleWait;
import org.firstinspires.ftc.teamcode.pedro.Constants;

import java.util.ArrayList;

public abstract class BasicAuto extends OpMode {

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
    private ArrayList<int[]> movement = new ArrayList<>();
    private int movementIndex = 0;
    private ArrayList<Action> actions = new ArrayList<>();
    protected int actionIndex = 0;
    private Action currentAction;
    private int[] currentTarget;

    // our poses
    private Pose[] poses;
    private int dualIndex = 0;

    // Will be overwritten
    /**
     * Use this method to call createAction
     */
    protected abstract void setActions();
    protected void createAction(int[] position, Action action){
        movement.add(position);
        actions.add(action);
    }
    protected void createAction(int[] position){
        movement.add(position);
        actions.add(new SimpleWait(0));
    }
    protected void createAction(Action action){
        movement.add(null);
        actions.add(action);
    }

    /**
     * Runs when pressing the 'init' button
     */
    protected abstract void opInit();

    /**
     * Runs every update loop
     */
    protected abstract void opLoop();

    private void hardwareInit(){
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
    }

    private void followerInit(){
        poses = new Pose[movement.size()];
        int poseCount = 0;
        for (int i = 0; i < movement.size(); i++){
            if (movement.get(i) != null) {
                poses[poseCount++] = poseFactory.of(movement.get(i)[0], movement.get(i)[1], movement.get(i)[2]);
            }
        }
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(poses[0]);
        follower.update();
    }

    @Override
    public void init() {
        hardwareInit();
        setActions();
        followerInit();
        opInit();
        Scheduler.reset();
    }

    private Path generatePath() {
        return line(poses[movementIndex], poses[movementIndex + 1]).linear(poses[movementIndex], poses[movementIndex + 1]);
    }

    @Override
    public void start() {
        stinger.setPosition(stingerTarget);
        rampServoL.setPosition(1.0f - rampTarget);
        rampServoR.setPosition(rampTarget);

        currentAction = actions.get(dualIndex++);
        currentTarget = movement.get(dualIndex);

        if (currentTarget != null){
            schedule(follow(follower, generatePath()));
            movementIndex++;
        }

        currentAction.init();
        currentAction.start();
    }

    @Override
    public void loop() {
        currentAction.update();
        currentAction.loop();
        follower.update();
        Scheduler.execute();

        if (follower.completion() > 0.975f && currentAction.isFinished()){
            currentAction.end();

            currentAction = actions.get(dualIndex++);
            if (dualIndex < movement.size() - 1){
                currentTarget = movement.get(dualIndex);
            }

            currentAction.init();
            currentAction.start();

            if (currentTarget != null){
                schedule(follow(follower, generatePath()));
                movementIndex++;
            }
        } // TODO: Add break condition if something gets stuck

        opLoop();
    }
}
