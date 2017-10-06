package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Ramsey on 10/15/2016.
 */

public class Hardware {

    public DcMotor LFMotor;
    public DcMotor LBMotor;
    public DcMotor RFMotor;
    public DcMotor RBMotor;

/*    public DcMotor conveyor;
    public DcMotor leftLift;
    public DcMotor rightLift;
    public DcMotor relicExtender;*/

    HardwareMap hwMap;

    public void init(HardwareMap hardwareMap) {
        this.hwMap = hardwareMap;

        LFMotor = hwMap.dcMotor.get("leftFrontMotor");
        LBMotor = hwMap.dcMotor.get("leftBackMotor");
        RFMotor = hwMap.dcMotor.get("rightFrontMotor");
        RBMotor = hwMap.dcMotor.get("rightBackMotor");

/*
        conveyor = hwMap.dcMotor.get("conveyor");
        leftLift = hwMap.dcMotor.get("leftLift");
        rightLift = hwMap.dcMotor.get("rightLift");
        relicExtender = hwMap.dcMotor.get("relicExtender");
*/

    }

}