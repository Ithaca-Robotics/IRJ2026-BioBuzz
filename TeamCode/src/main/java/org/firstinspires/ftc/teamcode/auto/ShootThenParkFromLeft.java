package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.BasicAuto;
import org.firstinspires.ftc.teamcode.lib.actions.FireInPlace;
import org.firstinspires.ftc.teamcode.lib.actions.SimpleWait;
import org.firstinspires.ftc.teamcode.lib.actions.SpindownLauncher;
import org.firstinspires.ftc.teamcode.lib.actions.SpinupLauncher;

@Autonomous
public class ShootThenParkFromLeft extends BasicAuto {
    @Override
    protected void setActions() {
        createAction(new int[] {8, 58, 180}, new SpinupLauncher(13000, this), 15000);
        createAction(new FireInPlace(8000, this), 9000);
        createAction(new SpindownLauncher(10, this), 500);
        createAction(new int[] {27, 54, 180}, 10000);
        createAction(new int[] {20, 12, 180}, 30000);
    }

    @Override
    protected void opInit() {

    }

    @Override
    protected void opLoop() {

    }
}
