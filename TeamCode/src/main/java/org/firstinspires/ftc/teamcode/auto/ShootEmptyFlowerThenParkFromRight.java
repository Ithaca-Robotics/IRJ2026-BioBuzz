package org.firstinspires.ftc.teamcode.auto;

import org.firstinspires.ftc.teamcode.lib.BasicAuto;

// Uncomment once new auto is created
// @Autonomous
public class ShootEmptyFlowerThenParkFromRight extends BasicAuto {
    @Override
    protected void setActions() {
        createAction(new int[] {138, 58, 0});
        createAction(new int[] {120, 30, 0});
        createAction(new int[] {96, 24, 270});
        createAction(new int[] {96, 17, 270});
        createAction(new int[] {96, 24, 270});
        createAction(new int[] {48, 12, 180});
    }

    @Override
    protected void opInit() {

    }

    @Override
    protected void opLoop() {

    }
}
