package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.control.Utils;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

/**
 * Created by manug on 12/3/2017.
 */

public class DumpPan implements Constants {

    private Servo dumpLeft;
    private Servo dumpRight;
    private Servo panGripper;
    private Utils.AutonomousOpMode auto;

    //private double pos = 0;
    public DumpPan(Hardware hardware) {
        dumpLeft = hardware.dumpLeft;
        dumpRight = hardware.dumpRight;
        panGripper = hardware.panGripper;
        auto = hardware.auto;
    }


    public void raisePan() {
/*
        for (double i = dumpRight.getPosition(); i <PAN_RAISE; i += 0.01) {
*/
            dumpRight.setPosition(PAN_RAISE);
            dumpLeft.setPosition(PAN_RAISE);
            //pos = 0;
/*            try {
                Thread.sleep(7);
            } catch (InterruptedException e){}
        }*/
    }

    public void raisePanAuto() {
        for (double i = dumpRight.getPosition(); i < PAN_RAISE; i += 0.01) {
            if (!opModeIsActive()) {
                break;
            }
            dumpRight.setPosition(PAN_RAISE);
            dumpLeft.setPosition(PAN_RAISE);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e){}
        }
    }


    public void raisePanAuto(int time) {
        for (double i = dumpRight.getPosition(); i < PAN_RAISE; i += 0.01) {
            if (!opModeIsActive()) {
                break;
            }
            dumpRight.setPosition(i);
            dumpLeft.setPosition(i);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e){}
        }
    }

    public void lowerPan() {
/*
        for (double i = dumpRight.getPosition(); i <PAN_LOWER; i += 0.01) {
*/
            dumpRight.setPosition(PAN_LOWER);
            dumpLeft.setPosition(PAN_LOWER);
            //pos = 0;

/*            try {
                Thread.sleep(7);
            } catch (InterruptedException e){}
        }*/
    }

    public void centerPan() {

/*
        if (dumpRight.getPosition() > PAN_CENTER) {
*/
            dumpRight.setPosition(PAN_CENTER);
            dumpLeft.setPosition(PAN_CENTER);
/*        } else {
            pos += 0.1;
            dumpRight.setPosition(pos);
            dumpLeft.setPosition(pos > PAN_CENTER ? PAN_CENTER : pos);
        }*/

    }

    public void closeGripper() {
        panGripper.setPosition(PAN_GRIP);
    }

    public void openGripper() {
        panGripper.setPosition(PAN_OPEN);
    }

    public boolean opModeIsActive() {
        return auto.getOpModeIsActive();
    }

}
