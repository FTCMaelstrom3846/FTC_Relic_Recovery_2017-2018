package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.control.Constants;

/**
 * Created by manug on 3/28/2018.
 */

public class ExternalEncoder implements Constants{
    DcMotor motor;
    private int previousPos = 0;
    private long previousTime = 0;
    private double rpm = 0;
    private double power;

    public ExternalEncoder (DcMotor motor) {
        this.motor = motor;
    }

    public int getPosition () {
        return motor.getCurrentPosition();
    }

    public double getRPM() {
        int deltaPos = motor.getCurrentPosition() - previousPos;
        double deltaTime = (System.nanoTime() - previousTime)/NANOSECONDS_PER_MINUTE;
        if (deltaTime*6e4 > 10) {
            rpm = (deltaPos/ E4T_COUNTS_PER_REV)/(deltaTime);
            previousPos = motor.getCurrentPosition();
            previousTime = System.nanoTime();
        }
        return rpm;
    }

    public void reset() {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
