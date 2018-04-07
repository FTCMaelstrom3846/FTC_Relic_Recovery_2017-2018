package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by manug on 3/28/2018.
 */

public class ExternalEncoder {
    DcMotor motor;

    public ExternalEncoder (DcMotor motor) {
        this.motor = motor;
    }

    public int getValue () {
        return motor.getCurrentPosition();
    }
}
