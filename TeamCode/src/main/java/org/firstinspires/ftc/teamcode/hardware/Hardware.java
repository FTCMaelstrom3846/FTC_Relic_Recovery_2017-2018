package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Ramsey on 10/15/2016.
 */

public class Hardware
{

    public DcMotor leftMotor;
    public DcMotor rightMotor;


    HardwareMap hwMap;


    public void init(HardwareMap hwMap)
    {
        this.hwMap = hwMap;

        leftMotor = hwMap.dcMotor.get("L motor");
        rightMotor = hwMap.dcMotor.get("R motor");
    }

}