package org.firstinspires.ftc.teamcode.lib.actions;

import org.firstinspires.ftc.teamcode.lib.Action;
import org.firstinspires.ftc.teamcode.lib.BasicAuto;

public class SpindownLauncher extends Action {

    public SpindownLauncher(float time, BasicAuto parent) {
        super(time, parent);
    }

    @Override
    public void start() {
        auto.setLauncherPower(0);
    }

    @Override
    public void loop() {

    }

    @Override
    public void end() {

    }
}
