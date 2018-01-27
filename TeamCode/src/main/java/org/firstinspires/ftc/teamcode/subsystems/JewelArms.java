package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

/**
 * Created by manug on 12/14/2017.
 */

public class JewelArms implements Constants {

    public Servo leftJewelArm;
    //public Servo rightJewelArm;
    public Servo jewelWrist;

    public JewelArms(Hardware hardware) {
        this.leftJewelArm = hardware.leftJewelArm;
        //this.rightJewelArm = hardware.rightJewelArm;
        this.jewelWrist = hardware.jewelWrist;
    }


    /*public void lowerRight() {
        rightJewelArm.setPosition(RIGHT_JEWEL_ARM_LOWER);
    }*/

    public void lowerLeft() {
        turnWristCenter();
        leftJewelArm.setPosition(LEFT_JEWEL_ARM_LOWER);
    }

/*    public void raiseRight() {
        rightJewelArm.setPosition(RIGHT_JEWEL_ARM_RAISE);
    }*/

    public void raiseLeft() {
        leftJewelArm.setPosition(LEFT_JEWEL_ARM_RAISE);
        try {
            Thread.sleep(100);
        }
        catch (InterruptedException e) {}
        turnWristLeft();
    }

    public void turnWristLeft() {
        jewelWrist.setPosition(JEWEL_WRIST_LEFT);
    }

    public void turnWristRight() {
        jewelWrist.setPosition(JEWEL_WRIST_RIGHT);
    }

    public void turnWristCenter() {
        jewelWrist.setPosition(JEWEL_WRIST_CENTER);
    }

}
