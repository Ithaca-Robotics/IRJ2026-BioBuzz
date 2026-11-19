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
        createAction(new int[] {138, 56, 0}, new SpinupLauncher(9000, this), 10000);
        createAction(new FireInPlace(6000, this), 7000);
        createAction(new int[] {120, 30, 0}, 2000);
        createAction(new int[] {94, 24, 270}, 2000);
        createAction(new LowerRamp(500, this), 1000);
        createAction(new int[] {95, 17, 270}, 1000);
        for (int i = 0; i < 4; i++){
            createAction(new int[] {98, 17, 270}, 1000);
            createAction(new int[] {95, 17, 260}, 1000);
        }
        createAction(new SimpleWait(2000, this), 3000);
        createAction(new int[] {95, 24, 270}, 1500);
        createAction(new RaiseRamp(500, this), 1000);
        createAction(new int[] {48, 14, 180}, 5000);
    }

    @Override
    protected void opInit() {

    }

    @Override
    protected void opLoop() {

    }
}
