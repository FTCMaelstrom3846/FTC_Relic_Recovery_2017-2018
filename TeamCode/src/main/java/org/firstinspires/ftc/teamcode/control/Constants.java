package org.firstinspires.ftc.teamcode.control;

/**
 * Created by Ramsey on 10/23/2017.
 */

public interface Constants {

    double frontLeftKP = 0.01;
    double frontLeftKI = 0;
    double frontLeftKD = 0;
    double frontLeftMaxI = 4;

    double frontRightKP = 0.01;
    double frontRightKI = 0;
    double frontRightKD = 0;
    double frontRightMaxI = 4;

    double backLeftKP = 0.01;
    double backLeftKI = 0;
    double backLeftKD = 0;
    double backLeftMaxI = 4;

    double backRightKP = 0.01;
    double backRightKI = 0;
    double backRightKD = 0;
    double backRightMaxI = 4;

    double NANOSECONDS_PER_MINUTE = 6e+10;
    int NEVEREST_20_MAX_RPM = 340;
    double NEVEREST_20_COUNTS_PER_REV = 537.6;

    double angleCorrectionKP = 0.01;
    double angleCorrectionKI = 0;
    double angleCorrectionKD = 0;
    double angleCorrectionMaxI = 0;
}
