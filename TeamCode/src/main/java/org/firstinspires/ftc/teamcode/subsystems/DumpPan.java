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

    private double pos = 0;
    public DumpPan(Hardware hardware) {
        this.dumpLeft = hardware.dumpLeft;
        this.dumpRight = hardware.dumpRight;
    }


    public void raisePan() {
/*
        for (double i = dumpRight.getPosition(); i <PAN_RAISE; i += 0.01) {
*/
            dumpRight.setPosition(PAN_RAISE);
            dumpLeft.setPosition(PAN_RAISE);
            pos = 0;
/*            try {
                Thread.sleep(7);
            } catch (InterruptedException e){}
        }*/
    }

    public void lowerPan() {
/*
        for (double i = dumpRight.getPosition(); i <PAN_LOWER; i += 0.01) {
*/
            dumpRight.setPosition(PAN_LOWER);
            dumpLeft.setPosition(PAN_LOWER);
            pos = 0;

/*            try {
                Thread.sleep(7);
            } catch (InterruptedException e){}
        }*/
    }

    public void centerPan() {

        if (dumpRight.getPosition() == 1) {
            dumpRight.setPosition(PAN_CENTER_RIGHT);
            dumpLeft.setPosition(PAN_CENTER_LEFT);
        } else {
            if (pos < PAN_CENTER_RIGHT) {
                pos += 0.1;
                dumpRight.setPosition(pos);
                dumpLeft.setPosition(pos > PAN_CENTER_LEFT ? PAN_CENTER_LEFT : pos);
            }
        }

    }

}
