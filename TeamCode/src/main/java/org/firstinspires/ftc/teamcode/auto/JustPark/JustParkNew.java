package org.firstinspires.ftc.teamcode.auto.JustPark;

import org.firstinspires.ftc.teamcode.lib.Action;
import org.firstinspires.ftc.teamcode.lib.BasicAuto;

public class JustParkNew extends BasicAuto {
    @Override
    protected void setActions() {
        createAction(new int[] {2, 54, 180});
        createAction(new int[] {27, 54, 180});
        createAction(new int[] {12, 2, 180});
    }

    @Override
    protected void opInit() {

    }

    @Override
    protected void opLoop() {

    }
}
