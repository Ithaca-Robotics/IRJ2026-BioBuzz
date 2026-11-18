package org.firstinspires.ftc.teamcode.lib.actions;

import org.firstinspires.ftc.teamcode.lib.Action;
import org.firstinspires.ftc.teamcode.lib.BasicAuto;

public class SpinupLauncher extends Action {

    public SpinupLauncher(float time, BasicAuto parent) {
        super(time, parent);
    }

    @Override
    public void start() {
        auto.setLauncherPower(0.5f);
    }

    @Override
    public void loop() {

    }

    @Override
    public void end() {

    }
}
