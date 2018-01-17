package org.firstinspires.ftc.teamcode.control;

/**
 * Created by manug on 1/16/2018.
 */

public class Utils {

    public static double clipValue (double input, double clipRange) {

        double clippedValue = Math.min(clipRange, Math.max(-clipRange, input));

        return clippedValue;
    }

    public static double clipValueTORange (double input, double lowerRange, double upperRange) {

        double clippedValue = Math.min(upperRange, Math.max(lowerRange, input));

        return clippedValue;
    }

    public static void normalizeValues (double[] values) {

        double maxValue = 0;

        for (int i = 0; i < values.length; i++) {
            maxValue = Math.max(maxValue, Math.abs(values[i]));
        }


        for (int i = 0; i < values.length; i++) {
            values[i] /= maxValue;
        }
    }

}
