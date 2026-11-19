package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.BasicAuto;

@Autonomous
public class ParkFromLeftCorner extends BasicAuto {
    @Override
    protected void setActions() {
        createAction(new int[] {8, 8, 180}, 1000);
        createAction(new int[] {20, 12, 180}, 30000);
    }

    @Override
    protected void opInit() {

    }

    @Override
    protected void opLoop() {

    }
}