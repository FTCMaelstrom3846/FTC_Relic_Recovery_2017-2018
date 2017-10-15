package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.control.SpeedControlledMotor;

/**
 * Created by Ramsey on 10/15/2016.
 */

public class Hardware {

    public SpeedControlledMotor frontLeft = new SpeedControlledMotor()/*,
            frontRight= new SpeedControlledMotor(),
            backLeft= new SpeedControlledMotor(),
            backRight= new SpeedControlledMotor()*/;



/*    public DcMotor conveyor;
    public DcMotor leftLift;
    public DcMotor rightLift;
    public DcMotor relicExtender;*/

    HardwareMap hwMap;

    public void init(HardwareMap hardwareMap) {

        this.hwMap = hardwareMap;

        frontLeft.init(hwMap, "frontLeft");
        /*frontRight.init(hwMap, "frontRight");
        backLeft.init(hwMap, "backLeft");
        backRight.init(hwMap, "backRight");*/

        SpeedControlledMotor[] motors = {frontLeft, /*backLeft, frontRight, backRight*/};

        for(SpeedControlledMotor motor: motors) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

/*
        conveyor = hwMap.dcMotor.get("conveyor");
        leftLift = hwMap.dcMotor.get("leftLift");
        rightLift = hwMap.dcMotor.get("rightLift");
        relicExtender = hwMap.dcMotor.get("relicExtender");
*/

    }

}