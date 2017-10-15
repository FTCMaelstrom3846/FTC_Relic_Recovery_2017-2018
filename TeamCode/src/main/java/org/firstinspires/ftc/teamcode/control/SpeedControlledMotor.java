package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.hardware.DcMotor;


public class SpeedControlledMotor {
    DcMotor motor;

    private double
            KP = 0.005,
            KI = 0,
            KD = 0,
            maxI = 4;

    public SpeedControlledMotor(DcMotor motor) {
        this.motor = motor;
    }
    public SpeedControlledMotor(DcMotor motor, double KP, double KI, double KD, double maxI, double minI) {
        this.motor = motor;
        this.KP = KP;
        this.KI = KI;
        this.KD = KD;
        this.maxI = maxI;
    }

    PIDController PIDController = new PIDController(KP, KI, KD, maxI);
    MeasureRPM measureRPM = new MeasureRPM(motor);

    int NEVEREST_20_RPM = 340;


    public void setPower(double speed) {
        motor.setPower(speed);
    }

    public void setSpeed(double speed, double dt) {
        double RPM = NEVEREST_20_RPM*speed;
        motor.setPower(PIDController.power(RPM, measureRPM.getRPM(dt), 1, dt));
    }

    public void setRPM(double RPM, double dt) {

    }
}
