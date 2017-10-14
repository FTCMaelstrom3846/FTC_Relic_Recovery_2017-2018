package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Ramsey on 10/15/2016.
 */

public class Hardware {

    public DcMotor FLMotor;
    public DcMotor BLMotor;
    public DcMotor FRMotor;
    public DcMotor BRMotor;

/*    public DcMotor conveyor;
    public DcMotor leftLift;
    public DcMotor rightLift;
    public DcMotor relicExtender;*/

    HardwareMap hwMap;

    public void init(HardwareMap hardwareMap) {
        this.hwMap = hardwareMap;

        FLMotor = hwMap.dcMotor.get("leftFrontMotor");
        BLMotor = hwMap.dcMotor.get("leftBackMotor");
        FRMotor = hwMap.dcMotor.get("rightFrontMotor");
        BRMotor = hwMap.dcMotor.get("rightBackMotor");


        FLMotor.setPower(0);
        BLMotor.setPower(0);
        FRMotor.setPower(0);
        BRMotor.setPower(0);

        FRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


/*
        conveyor = hwMap.dcMotor.get("conveyor");
        leftLift = hwMap.dcMotor.get("leftLift");
        rightLift = hwMap.dcMotor.get("rightLift");
        relicExtender = hwMap.dcMotor.get("relicExtender");
*/

    }

}