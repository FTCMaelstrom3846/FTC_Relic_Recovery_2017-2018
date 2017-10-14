package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.control.PID;
import org.firstinspires.ftc.teamcode.control.MeasureRPM;


public class SpeedControlledMotor {
    DcMotor motor;
    public SpeedControlledMotor(DcMotor motor) {
        this.motor = motor;
    }
    public void setPower(double speed) {
        motor.setPower(speed);
    }

    public void setSpeed
}
