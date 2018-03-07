package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

/**
 * Created by manug on 12/14/2017.
 */

public class JewelArm implements Constants {

    public Servo jewelArm;
    public Servo jewelWrist;

    public JewelArm(Hardware hardware) {
        this.jewelArm = hardware.jewelArm;
        this.jewelWrist = hardware.jewelWrist;
    }

    public void lower() {
        turnWristCenter();
        jewelArm.setPosition(JEWEL_ARM_LOWER);
    }


    public void raise() {
        jewelArm.setPosition(JEWEL_ARM_RAISE);
        try {
            Thread.sleep(100);
        }
        catch (InterruptedException e) {}
        resetWrist();
    }

    public void resetWrist() {
        jewelWrist.setPosition(JEWEL_WRIST_RESET);
    }

    public void turnWristRight() {
        jewelWrist.setPosition(JEWEL_WRIST_RIGHT);
    }

    public void turnWristLeft() {
        jewelWrist.setPosition(JEWEL_WRIST_LEFT);
    }

    public void turnWristCenter() {
        jewelWrist.setPosition(JEWEL_WRIST_CENTER);
    }

}
