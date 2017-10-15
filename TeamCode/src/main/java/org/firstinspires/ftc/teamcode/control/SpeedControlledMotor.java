package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.control.PIDConstants;


public class SpeedControlledMotor {
    private DcMotor motor;
    private int NEVEREST_20_RPM = 340;
    private int previousPos = 0;
    double previousTime = 0;

    private double
            KP = 0.005,
            KI = 0,
            KD = 0,
            maxI = 4;

    PIDController PIDController = new PIDController(KP, KI, KD, maxI);

    public SpeedControlledMotor() {}

    public SpeedControlledMotor(double KP, double KI, double KD, double maxI) {
        this.KP = KP;
        this.KI = KI;
        this.KD = KD;
        this.maxI = maxI;
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
        double deltaTime = System.nanoTime() - previousTime;
        previousPos = motor.getCurrentPosition();
        previousTime = System.nanoTime();
        return 6e4*(deltaPos/deltaTime)/1120;
    }

    public void setSpeed(double speed) {
        double RPM = NEVEREST_20_RPM*speed;
        motor.setPower(PIDController.power(RPM, getRPM()));
    }

    public void setRPM(double RPM, double dt) {

    }
}
