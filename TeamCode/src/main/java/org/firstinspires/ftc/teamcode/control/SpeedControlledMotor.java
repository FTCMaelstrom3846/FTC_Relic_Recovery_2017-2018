package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class SpeedControlledMotor implements Constants{
    private DcMotor motor;
    private int previousPos = 0;
    private long previousTime = 0;
    private double rpm = 0;

    PIDController PIDController;

    public SpeedControlledMotor(double KP, double KI, double KD, double maxI) {
        this.PIDController = new PIDController(KP, KI, KD, maxI);
    }


    public void init(HardwareMap hardwareMap, String name) {
        motor = hardwareMap.dcMotor.get(name);
    }

    public void setPower(double power) {
        motor.setPower(power);
    }

    public void setMode(DcMotor.RunMode runMode) {
        motor.setMode(runMode);
    }

    public double getRPM() {
        int deltaPos = motor.getCurrentPosition() - previousPos;
        double deltaTime = (System.nanoTime() - previousTime)/NANOSECONDS_PER_MINUTE;
        if (deltaTime*6e4 > 10) {
            rpm = (deltaPos/ NEVEREST_20_COUNTS_PER_REV)/(deltaTime);
            previousPos = motor.getCurrentPosition();
            previousTime = System.nanoTime();
        }
        return rpm;
    }

    public void setSpeed(double speed) {
        double rpm = NEVEREST_20_MAX_RPM *speed;
        motor.setPower(
                ((PIDController.power(rpm, getRPM()) > 0 && rpm < 0) ||
                (PIDController.power(rpm, getRPM()) < 0 && rpm > 0)) ?
                0: (PIDController.power(rpm, getRPM())));
    }

    public void setRPM(double rpm) {
        motor.setPower(PIDController.power(rpm, getRPM()));

    }

    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }
}
