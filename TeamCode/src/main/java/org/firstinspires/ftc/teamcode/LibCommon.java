package org.firstinspires.ftc.teamcode;

import com.pedropathing.math.Pose;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class LibCommon {
    /**
     * Converts the standard FTC coordinate system to PedroPathing's native coordinate system. The inverse of pedroToFtc. Stolen directly from <a href="https://pedropathing.com/docs/pathing/reference/coordinates">PedroPathing's website</a>
     * @param ftcPose The FTC 'Pose', or coordinates
     * @return The equivalent 'Pose' for PedroPathing
     */
    public static Pose ftcToPedro(Pose2D ftcPose){
        double x = ftcPose.getX(DistanceUnit.INCH);
        double y = ftcPose.getY(DistanceUnit.INCH);
        double heading = ftcPose.getHeading(AngleUnit.RADIANS);

        return new Pose(
            y + 72,
            72 - x,
            heading - Math.PI / 2
        );
    }

    /**
     * Converts the standard FTC coordinate system to PedroPathing's native coordinate system. The inverse of ftcToPedro. Adapted from <a href="https://pedropathing.com/docs/pathing/reference/coordinates">PedroPathing's website</a>
     * @param pedroPose The PedroPathing 'Pose', or coordinates
     * @return The equivalent 'Pose' for FTC
     */
    public static Pose2D pedroToFtc(Pose pedroPose){
        double x = pedroPose.x();
        double y = pedroPose.y();
        double heading = pedroPose.heading();

        return new Pose2D(
            DistanceUnit.INCH,
            72 - y,
            x - 72,
            AngleUnit.RADIANS,
            heading + Math.PI / 2
        );
    }

    public enum Actions{
        INIT,
        TOGGLE_FIRE,
        SHUTDOWN,
    }
}
