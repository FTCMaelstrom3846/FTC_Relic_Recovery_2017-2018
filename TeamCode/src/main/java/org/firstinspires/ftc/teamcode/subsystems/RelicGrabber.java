package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.control.SpeedControlledMotor;
import org.firstinspires.ftc.teamcode.hardware.Hardware;


/**
 * Created by Ramsey on 10/5/2017.
 */

public class RelicGrabber implements Constants {

    private SpeedControlledMotor relicExtender;
    public Servo relicWrist;
    public Servo relicGrabber;

    public RelicGrabber(Hardware hardware) {
        //this.gamepad1 = gamepad1;
        this.relicExtender = hardware.relicExtender;
        this.relicWrist = hardware.relicWrist;
        this.relicGrabber = hardware.relicGrabber;
    }


    public void extend() {
        relicExtender.setPower(RELIC_EXTENDER_POWER);
    }

    public void retract() {
        relicExtender.setPower(RELIC_RETRACT_POWER);
    }

    public void stop() {
        relicExtender.setPower(0);
    }

    public void setPower(double power) {
        relicExtender.setPower(power);
    }

    public void openGrabber() {
        relicGrabber.setPosition(RELIC_GRABBER_OPEN);
    }

    public void closeGrabber() {
        relicGrabber.setPosition(RELIC_GRABBER_CLOSED);
    }

    public void resetWrist() {
        relicWrist.setPosition(RELIC_WRIST_RESET);
    }

    public void raiseWrist() {
        relicWrist.setPosition(RELIC_WRIST_UP);
    }

    public void lowerWrist() {
        relicWrist.setPosition(relicExtender.getCurrentPosition() > 3000 ? RELIC_WRIST_DROP : RELIC_WRIST_PICKUP);
    }

}