package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.hardware.Hardware;


/**
 * Created by Ramsey on 10/5/2017.
 */

public class Conveyor implements Constants {

    private CRServo conveyorTopRight;
    private CRServo conveyorTopLeft;
    private CRServo conveyorBottomRight;
    private CRServo conveyorBottomLeft;

    private CRServo[] conveyorServos = {conveyorTopRight, conveyorTopLeft, conveyorBottomRight, conveyorBottomLeft};

    public Conveyor (Hardware hardware) {
        this.conveyorServos = hardware.conveyorServos;
        //this.gamepad1 = gamepad1;
        /*this.intakeTopRight = hardware.conveyorTopRight;
        this.intakeTopLeft = hardware.conveyorTopLeft;
        this.intakeBottomRight = hardware.conveyorBottomRight;
        this.intakeBottomLeft = hardware.conveyorBottomLeft;*/
    }


    public void setSpeed(double speed) {
        for (CRServo servo: conveyorServos) {
            servo.setPower(speed);
        }
    }
    public void up() {
        for (CRServo servo: conveyorServos) {
            servo.setPower(CONVEYOR_UP_POWER);
        }
    }
    public void down() {
        for (CRServo servo: conveyorServos) {
            servo.setPower(CONVEYOR_DOWN_POWER);
        }
    }
    public void stop() {
        for (CRServo servo: conveyorServos) {
            servo.setPower(0);
        }
    }
}