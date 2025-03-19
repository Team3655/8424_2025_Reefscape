package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;

public class WristConstants {

    // MAX WRIST = 90 degrees
    // MIN WRIST = -90 degrees

    /*
     * -90 is all the way down
     * 90 is all the way up
     * 0 is horizontal
     */
    public static double WRIST_LEVEL_2 = Units.degreesToRotations(27);  //TODO: Change this value
    public static double WRIST_LEVEL_3 = Units.degreesToRotations(28);  //TODO: Change this value
    public static double WRIST_LEVEL_4 = Units.degreesToRotations(28); //TODO: Change this value
    public static double WRIST_LEVEL_FEEDER = Units.degreesToRotations(-90);
    public static double WRIST_LEVEL_START = Units.degreesToRotations(-90);
    public static double WRIST_LET_GO = Units.degreesToRotations(10); //TODO: Change this value
    public static double TEST = Units.degreesToRotations(-45);
    public static double TRANSITION_STATE = Units.degreesToRotations(45);
}
