package org.firstinspires.ftc.teamcode.lib.actions;

import org.firstinspires.ftc.teamcode.lib.Action;
import org.firstinspires.ftc.teamcode.lib.BasicAuto;

public class FireInPlace extends Action {

    public FireInPlace(float time, BasicAuto parent) {
        super(time, parent);
    }

    @Override
    public void start() {
        auto.setIntakePower(1);
        auto.setFiringStatus(true);
    }

    @Override
    public void loop() {

    }

    @Override
    public void end() {
        auto.setIntakePower(0);
        auto.setFiringStatus(false);
    }
}
