package org.firstinspires.ftc.teamcode.control;

/**
 * Created by Ramsey on 10/23/2017.
 */

public interface Constants {

    double frontLeftKP = 0.008;
    double frontLeftKI = 0;
    double frontLeftKD = 0/*1.92426108e-6*/;
    double frontLeftMaxI = 1;

    double frontRightKP = 0.008;
    double frontRightKI = 0;
    double frontRightKD = 0;
    double frontRightMaxI = 1;

    double backLeftKP = 0.008;
    double backLeftKI = 0;
    double backLeftKD = 0;
    double backLeftMaxI = 1;

    double backRightKP = 0.008;
    double backRightKI = 0;
    double backRightKD = 0;
    double backRightMaxI = 1;

    double NANOSECONDS_PER_MINUTE = 6e+10;
    int NEVEREST_20_MAX_RPM = 290;
    double NEVEREST_20_COUNTS_PER_REV = 537.6;

    double angleCorrectionKP = 0.01;
    double angleCorrectionKI = 0;
    double angleCorrectionKD = 0;
    double angleCorrectionMaxI = 0;
}
