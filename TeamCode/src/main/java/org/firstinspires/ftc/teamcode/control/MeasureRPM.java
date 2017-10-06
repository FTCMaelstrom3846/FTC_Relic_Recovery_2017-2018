package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.hardware.DcMotor;

public class MeasureRPM {
    private int previousPos;
    private DcMotor motor;

    public MeasureRPM(DcMotor motor) {this.motor = motor;}

    public double getRPM(double deltaTime) {
        int deltaPos = motor.getCurrentPosition() - previousPos;
        previousPos = motor.getCurrentPosition();
        return 6e4*(deltaPos/deltaTime)/1120;
    }

    public void setInitalPos(int previousPos) {this.previousPos = previousPos;}
}
