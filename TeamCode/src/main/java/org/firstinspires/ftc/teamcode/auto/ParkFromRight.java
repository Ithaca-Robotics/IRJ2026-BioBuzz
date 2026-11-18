package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.lib.BasicAuto;

 @Autonomous
public class ParkFromRight extends BasicAuto {
    @Override
    protected void setActions() {
        createAction(new int[] {136, 58, 0});
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
