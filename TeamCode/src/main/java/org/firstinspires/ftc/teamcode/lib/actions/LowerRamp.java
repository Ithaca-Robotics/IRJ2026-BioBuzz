package org.firstinspires.ftc.teamcode.lib.actions;

import org.firstinspires.ftc.teamcode.lib.Action;
import org.firstinspires.ftc.teamcode.lib.BasicAuto;

public class LowerRamp extends Action {
    public LowerRamp(double time, BasicAuto parent) {
        super(time, parent);
    }

    @Override
    public void start() {
        auto.setRampPosition(0.9f);
        auto.setIntakePower(1);
    }

    @Override
    public void loop() {

    }

    @Override
    public void end() {

    }
}
