package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.hardware.Hardware;


/**
 * Created by Ramsey on 10/5/2017.
 */

public class Conveyor {

    CRServo intakeTopRight;
    CRServo intakeTopLeft;
    CRServo intakeBottomRight;
    CRServo intakeBottomLeft;

    public CRServo[] intakeServos = {intakeTopRight, intakeTopLeft, intakeBottomRight, intakeBottomLeft};

    public Conveyor (Hardware hardware) {
        //this.gamepad1 = gamepad1;
        /*this.intakeTopRight = hardware.conveyorTopRight;
        this.intakeTopLeft = hardware.conveyorTopLeft;
        this.intakeBottomRight = hardware.conveyorBottomRight;
        this.intakeBottomLeft = hardware.conveyorBottomLeft;*/
    }


    public void run(double speed) {
        for(CRServo servo: intakeServos) {
            servo.setPower(speed);
        }
    }
}