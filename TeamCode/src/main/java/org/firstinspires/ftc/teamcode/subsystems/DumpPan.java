package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

/**
 * Created by manug on 12/3/2017.
 */

public class DumpPan implements Constants {

    public Servo dumpLeft;
    public Servo dumpRight;

    public DumpPan(Hardware hardware) {
        this.dumpLeft = hardware.dumpLeft;
        this.dumpRight = hardware.dumpRight;
    }


    public void raisePan() {
        dumpRight.setPosition(PAN_RAISE);
        dumpLeft.setPosition(PAN_RAISE);
    }

    public void lowerPan() {
        dumpRight.setPosition(PAN_LOWER);
        dumpLeft.setPosition(PAN_LOWER);
    }

    public void centerPan() {
        dumpRight.setPosition(PAN_CENTER);
        dumpLeft.setPosition(PAN_CENTER);
    }

}
