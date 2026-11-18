package org.firstinspires.ftc.teamcode.lib.actions;

import org.firstinspires.ftc.teamcode.lib.Action;
import org.firstinspires.ftc.teamcode.lib.BasicAuto;

public class RaiseRamp extends Action {
    public RaiseRamp(double time, BasicAuto parent) {
        super(time, parent);
    }

    @Override
    public void start() {
        auto.setRampPosition(0);
        auto.setIntakePower(0);
    }

    @Override
    public void loop() {

    }

    @Override
    public void end() {

    }
}
