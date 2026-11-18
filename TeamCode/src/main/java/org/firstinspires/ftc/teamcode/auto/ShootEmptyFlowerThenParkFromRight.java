package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.BasicAuto;
import org.firstinspires.ftc.teamcode.lib.actions.FireInPlace;
import org.firstinspires.ftc.teamcode.lib.actions.LowerRamp;
import org.firstinspires.ftc.teamcode.lib.actions.RaiseRamp;
import org.firstinspires.ftc.teamcode.lib.actions.ShakeRamp;
import org.firstinspires.ftc.teamcode.lib.actions.SimpleWait;
import org.firstinspires.ftc.teamcode.lib.actions.SpinupLauncher;

 @Autonomous
public class ShootEmptyFlowerThenParkFromRight extends BasicAuto {
    @Override
    protected void setActions() {
        createAction(new int[] {138, 56, 0}, new SpinupLauncher(8000, this));
        createAction(new FireInPlace(6000, this));
        createAction(new int[] {120, 30, 0});
        createAction(new int[] {94, 24, 270});
        createAction(new LowerRamp(500, this));
        createAction(new int[] {94, 18, 270});
        createAction(new int[] {93, 18, 260});
        createAction(new int[] {94, 18, 270});
        createAction(new int[] {93, 18, 260});
        createAction(new int[] {94, 18, 270});
        createAction(new SimpleWait(2000, this));
        createAction(new int[] {94, 24, 270});
        createAction(new RaiseRamp(500, this));
        createAction(new int[] {48, 14, 180});
    }

    @Override
    protected void opInit() {

    }

    @Override
    protected void opLoop() {

    }
}
