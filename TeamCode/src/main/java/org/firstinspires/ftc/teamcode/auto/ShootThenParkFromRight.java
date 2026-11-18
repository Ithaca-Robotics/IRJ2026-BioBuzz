package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.BasicAuto;
import org.firstinspires.ftc.teamcode.lib.actions.FireInPlace;
import org.firstinspires.ftc.teamcode.lib.actions.SpindownLauncher;
import org.firstinspires.ftc.teamcode.lib.actions.SpinupLauncher;

 @Autonomous
public class ShootThenParkFromRight extends BasicAuto {
    @Override
    protected void setActions() {
        createAction(new int[] {136, 58, 0}, new SpinupLauncher(8000, this));
        createAction(new FireInPlace(10000, this));
        createAction(new SpindownLauncher(10, this));
        createAction(new int[] {110, 26, 0});
        createAction(new int[] {90, 40, 180});
        createAction(new int[] {48, 12, 180});
    }

    @Override
    protected void opInit() {

    }

    @Override
    protected void opLoop() {

    }
}
