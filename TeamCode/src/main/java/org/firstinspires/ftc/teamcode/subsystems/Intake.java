package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.hardware.Hardware;


/**
 * Created by Ramsey on 10/5/2017.
 */

public class Intake implements Constants {

    private DcMotor leftIntake;
    private DcMotor rightIntake;

    public CRServo intakeDropper;

    public Intake (Hardware hardware) {
        //this.gamepad1 = gamepad1;
        this.leftIntake = hardware.leftIntake;
        this.rightIntake = hardware.rightIntake;
        this.intakeDropper = hardware.intakeDropper;
    }


    public void intake() {
        leftIntake.setPower(INTAKESYSTEM_INTAKE_POWER);
        rightIntake.setPower(INTAKESYSTEM_INTAKE_POWER);
    }

    public void outtake() {
        leftIntake.setPower(INTAKESYSTEM_OUTAKE_POWER);
        rightIntake.setPower(INTAKESYSTEM_OUTAKE_POWER);
    }

    public void stop() {
        leftIntake.setPower(0);
        rightIntake.setPower(0);
    }

    public void extendDropper() {
        intakeDropper.setPower(DROPPER_EXTEND_POWER);
    }

    public void retractDropper() {
        intakeDropper.setPower(DROPPER_RETRACT_POWER);
    }

    public void stopDropper() {
        intakeDropper.setPower(0);
    }
}