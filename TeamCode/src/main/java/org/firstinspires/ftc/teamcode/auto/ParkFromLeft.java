package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.BasicAuto;
import org.firstinspires.ftc.teamcode.lib.actions.SimpleWait;

@Autonomous
public class ParkFromLeft extends BasicAuto {
    @Override
    protected void setActions() {
        createAction(new int[] {8, 58, 180}, new SimpleWait(15000, this));
        createAction(new int[] {27, 54, 180});
        createAction(new int[] {20, 12, 180});
    }

    @Override
    protected void opInit() {

    }

    @Override
    protected void opLoop() {

    }
}
