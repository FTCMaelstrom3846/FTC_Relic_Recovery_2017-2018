package org.firstinspires.ftc.teamcode.control;

/**
 * Created by manug on 1/16/2018.
 */

public class Utils {


    public static double clipValue (double input, double clipRange) {

        double clippedValue = Math.min(clipRange, Math.max(-clipRange, input));

        return clippedValue;
    }

    public static void clipValue (double[] input, double clipRange) {

        for (int i = 0; i < input.length; i++) {
            input[i] = Math.min(clipRange, Math.max(-clipRange, input[i]));
        }

    }

    public static double clipValueToRange (double input, double lowerRange, double upperRange) {

        double clippedValue = Math.min(upperRange, Math.max(lowerRange, input));

        return clippedValue;
    }

    public static void  clipValueToRange (double[] input, double lowerRange, double upperRange) {

        for (int i = 0; i < input.length; i++) {
            input[i] = Math.min(upperRange, Math.max(lowerRange, input[i]));
        }
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

    public static void normalizeSpeedsToMax (double[] speeds, double maxSpeed) {
        double maxValue = 0;

        for (int i = 0; i < speeds.length; i++) {
            maxValue = Math.max(maxValue, Math.abs(speeds[i]));
        }

        if (maxValue > maxSpeed) {
            for (int i = 0; i < speeds.length; i++) {
                speeds[i] /= maxValue;
                speeds[i] *= maxSpeed;
            }
        }
    }

}
