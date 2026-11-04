package org.firstinspires.ftc.teamcode.auto;

import org.firstinspires.ftc.teamcode.lib.BasicAuto;

public class ParkFromLeftCorner extends BasicAuto {
    @Override
    protected void setActions() {
        createAction(new int[] {2, 0, 180});
        createAction(new int[] {12, 2, 180});
    }

    @Override
    protected void opInit() {

    }

    @Override
    protected void opLoop() {

    }
}
