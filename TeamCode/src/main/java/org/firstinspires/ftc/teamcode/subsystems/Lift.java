package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.control.SpeedControlledMotor;
import org.firstinspires.ftc.teamcode.hardware.Hardware;


/**
 * Created by Ramsey on 10/5/2017.
 */

public class Lift implements Constants{

   /* private SpeedControlledMotor rightLift;
    private SpeedControlledMotor leftLift;*/

   private CRServo rightLift;
   private CRServo leftLift;

    public Lift(Hardware hardware) {
        //this.gamepad1 = gamepad1;
        this.leftLift = hardware.leftLift;
        this.rightLift = hardware.rightLift;
    }


    public void raise() {
        leftLift.setPower(LIFT_RAISE_POWER);
        rightLift.setPower(LIFT_RAISE_POWER);
    }
//hi hamsey ramed
/*    public void raiseRight() {
        rightLift.setPower(LIFT_RAISE_POWER);
    }

    public void raise() {
        leftLift.setPower(LIFT_RAISE_POWER);
    }*/

    public void lower() {
        leftLift.setPower(LIFT_LOWER_POWER);
        rightLift.setPower(LIFT_LOWER_POWER);
    }

/*    public void lowerRight() {
        rightLift.setPower(LIFT_LOWER_POWER);
    }

    public void lower() {
        leftLift.setPower(LIFT_LOWER_POWER);
    }*/

    public void stop() {
        leftLift.setPower(0);
        rightLift.setPower(0);
    }
}