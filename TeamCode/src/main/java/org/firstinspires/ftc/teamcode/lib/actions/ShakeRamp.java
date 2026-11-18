package org.firstinspires.ftc.teamcode.lib.actions;

import org.firstinspires.ftc.teamcode.lib.Action;
import org.firstinspires.ftc.teamcode.lib.BasicAuto;

public class ShakeRamp extends Action {
    private int speed;
    private float distance;
    private double lastTime;
    private boolean up = false;
    public ShakeRamp(double time, BasicAuto parent, int shakeSpeed, float shakeDistance) {
        super(time, parent);
        speed = shakeSpeed;
        distance = shakeDistance;
    }

    @Override
    public void start() {
        lastTime = time();
    }

    @Override
    public void loop() {
        if (time() - lastTime >= speed){
            if (up){
                auto.setRampPosition(1 - distance);
            }else{
                auto.setRampPosition(1);
            }
            up = !up;
            lastTime = time();
        }
    }

    @Override
    public void end() {
        auto.setRampPosition(1);
    }
}
