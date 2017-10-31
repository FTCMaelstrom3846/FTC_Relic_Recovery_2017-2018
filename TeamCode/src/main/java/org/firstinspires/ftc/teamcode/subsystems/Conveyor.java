package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.hardware.Hardware;


/**
 * Created by Ramsey on 10/5/2017.
 */

public class Conveyor {

    CRServo conveyorTopRight;
    CRServo conveyorTopLeft;
    CRServo conveyorBottomRight;
    CRServo conveyorBottomLeft;

    public CRServo[] conveyorServos = {/*conveyorTopRight, conveyorTopLeft,*/ conveyorBottomRight, conveyorBottomLeft};

    public Conveyor (Hardware hardware) {
/*
        this.conveyorServos = hardware.conveyorServos;
*/
        //this.gamepad1 = gamepad1;
        /*this.intakeTopRight = hardware.conveyorTopRight;
        this.intakeTopLeft = hardware.conveyorTopLeft;
        this.intakeBottomRight = hardware.conveyorBottomRight;
        this.intakeBottomLeft = hardware.conveyorBottomLeft;*/
    }


    public void run(double speed) {
        for (CRServo servo: conveyorServos) {
            servo.setPower(speed);
        }
    }
}