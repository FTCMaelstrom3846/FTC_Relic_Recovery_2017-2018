package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Ramsey on 10/15/2016.
 */

public class MinibotHardware {

    public DcMotor leftMotor;
    public DcMotor rightMotor;

    public DcMotor lift;

    public Servo rightGripper;
    public Servo leftGripper;

    public Servo relicWrist;
    public Servo relicGrabber;

    HardwareMap hwMap;

    public void init(HardwareMap hardwareMap) {
        this.hwMap = hardwareMap;

        leftMotor = hwMap.dcMotor.get("leftMotor");
        rightMotor = hwMap.dcMotor.get("rightMotor");

        lift = hwMap.dcMotor.get("lift");

        rightGripper = hwMap.servo.get("rightGripperServo");
        leftGripper = hwMap.servo.get("leftGripperServo");

        relicWrist = hwMap.servo.get("relicWrist");
        relicGrabber = hwMap.servo.get("relicGrabber");

    }

}