package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Ramsey on 10/15/2016.
 */

public class Hardware {


    public DcMotor frontLeft, backLeft, frontRight, backRight;



/*    public DcMotor conveyor;
    public DcMotor leftLift;
    public DcMotor rightLift;
    public DcMotor relicExtender;*/

    HardwareMap hwMap;

    public void init(HardwareMap hardwareMap) {

        this.hwMap = hardwareMap;

        frontLeft = hwMap.dcMotor.get("leftFrontMotor");
        backLeft = hwMap.dcMotor.get("leftBackMotor");
        frontRight = hwMap.dcMotor.get("rightFrontMotor");
        backRight = hwMap.dcMotor.get("rightBackMotor");

        DcMotor[] motors = {frontLeft, backLeft, frontRight, backRight};

        for(DcMotor motor: motors) {
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