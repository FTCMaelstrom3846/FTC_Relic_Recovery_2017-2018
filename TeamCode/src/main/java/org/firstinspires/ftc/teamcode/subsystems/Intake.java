package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.hardware.Hardware;


/**
 * Created by Ramsey on 10/5/2017.
 */

public class Intake implements Constants {

    private DcMotor intake;

    public Intake (Hardware hardware) {
        //this.gamepad1 = gamepad1;
        this.intake = hardware.intake;
    }


    public void intake() {
        intake.setPower(INTAKESYSTEM_INTAKE_POWER);
    }

    public void outtake() {
        intake.setPower(INTAKESYSTEM_OUTAKE_POWER);
    }

    public void stop() {intake.setPower(0);}
}