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
        createAction(new int[] {136, 58, 0}, new SpinupLauncher(9000, this), 10000);
        createAction(new FireInPlace(8000, this), 9000);
        createAction(new SpindownLauncher(10, this), 500);
        createAction(new int[] {110, 26, 0}, 5000);
        createAction(new int[] {90, 40, 180}, 5000);
        createAction(new int[] {48, 12, 180}, 5000);
    }

    @Override
    protected void opInit() {

    }

    @Override
    protected void opLoop() {

    }
}
