package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class Action {
    private boolean running = true;
    private double startTime;
    private ElapsedTime timer = new ElapsedTime();
    private double maxTime;
    protected BasicAuto auto;

    public Action(double time, BasicAuto parent){
        maxTime = time;
        auto = parent;
    }

    public boolean isFinished(){
        return !running;
    }

    public void init(){
        startTime = timer.milliseconds();
    }

    public double time(){
        return timer.milliseconds() - startTime;
    }

    public void update(){
        if (time() > maxTime){
            running = false;
        }
    }

    public abstract void start();
    public abstract void loop();
    public abstract void end();
}
