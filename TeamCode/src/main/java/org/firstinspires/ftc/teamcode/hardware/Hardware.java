package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Ramsey on 10/15/2016.
 */

public class Hardware {

    public DcMotor leftMotor;
    public DcMotor rightMotor;

    public Servo rightGripper;
    public Servo leftGripper;

    HardwareMap hwMap;

    public void init(HardwareMap hardwareMap) {
        this.hwMap = hardwareMap;

        leftMotor = hwMap.dcMotor.get("leftMotor");
        rightMotor = hwMap.dcMotor.get("rightMotor");

        rightGripper = hwMap.servo.get("rightGripperServo");
        leftGripper = hwMap.servo.get("leftGripperServo");

    }

}