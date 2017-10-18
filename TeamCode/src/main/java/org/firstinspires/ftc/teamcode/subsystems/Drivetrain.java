package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.control.SpeedControlledMotor;
import org.firstinspires.ftc.teamcode.opModes.MaelstromAutonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.sensors.BNO055_IMU;


/**
 * Created by Ramsey on 10/5/2017.
 */

public class Drivetrain {

    Gamepad gamepad1;
    SpeedControlledMotor frontLeft, backLeft, frontRight, backRight;
    boolean halfSpeed = false;
    BNO055_IMU imu;
    MaelstromAutonomous auto;

    public Drivetrain (Gamepad gamepad1, Hardware hardware/*, boolean halfSpeed*/) {
        this.gamepad1 = gamepad1;
        this.backLeft = hardware.backLeft;
        this.frontLeft = hardware.frontLeft;
        this.backRight = hardware.backRight;
        this.frontRight = hardware.frontRight;
        /*this.halfSpeed = halfSpeed;*/
    }

    public Drivetrain (Hardware hardware, BNO055_IMU imu, MaelstromAutonomous auto) {
        this.backLeft = hardware.backLeft;
        this.frontLeft = hardware.frontLeft;
        this.backRight = hardware.backRight;
        this.frontRight = hardware.frontRight;
        this.imu = imu;
        this.auto = auto;
    }

    public void drive() {

        double x = gamepad1.left_stick_x;
        double y = gamepad1.left_stick_y;
        double angle = Math.atan2(y, x);
        angle -= Math.PI/4;

        double speedMagnitude = Math.hypot(x, y);
        double frontLeftPower = -(Math.sin(angle) * speedMagnitude) + gamepad1.right_stick_x;
        double backLeftPower = -(Math.cos(angle) * speedMagnitude) + gamepad1.right_stick_x;
        double frontRightPower = (Math.cos(angle) * speedMagnitude) + gamepad1.right_stick_x;
        double backRightPower = (Math.sin(angle) * speedMagnitude) + gamepad1.right_stick_x;

        double driveScaleFactor = Math.abs(Math.max(
                Math.max(frontLeftPower, frontRightPower),
                Math.max(backLeftPower, backRightPower)))
                != 0 ? Math.abs(Math.max(
                Math.max(frontLeftPower, frontRightPower),
                Math.max(backLeftPower, backRightPower))) : 1
        ;


        frontLeftPower /= driveScaleFactor;
        frontRightPower /= driveScaleFactor; //this is a big meme i hate u archi
        backLeftPower /= driveScaleFactor;
        backRightPower /= driveScaleFactor;

        if (!halfSpeed) {
            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(backLeftPower);
            backLeft.setPower(frontRightPower);
            backRight.setPower(backRightPower);
        }
        else {
            frontLeft.setPower(0.5*frontLeftPower);
            frontRight.setPower(0.5*backLeftPower);
            backLeft.setPower(0.5*frontRightPower);
            backRight.setPower(0.5*backRightPower);
        }

    }


//Everything below is retarded old stuff and needs to be fixed

    /*public void drive(int rightEncoder





                      *//*Change to dirstance*//*



                      , double angle, double KP, double KI) {
        double frontLeftPower;
        double backLeftPower;
        double frontRightPower;
        double backRightPower;

        eReset();

        double error;
        long startTime = System.nanoTime();
        long stopState = 0;
        rightEncoder = -rightEncoder;
        PID.i = 0;
        double initialHeading = imu.getAngles()[0];
        double corrKP = 0.01;
        angle = angle * (Math.PI / 180);
        frontLeftPower = -(Math.sin(angle + (Math.PI / 4)));
        backLeftPower = -(Math.cos(angle + (Math.PI / 4)));
        frontRightPower = (Math.cos(angle + (Math.PI / 4)));
        backRightPower = (Math.sin(angle + (Math.PI / 4)));

        while (opModeIsActive() && (stopState <= 1000)) {
            error = imu.getAngles()[0] - initialHeading;
            frontLeft.setPower((frontLeftPower * PID.EncoderPID(rightEncoder, frontRight.getCurrentPosition(), KP, KI)) + (corrKP * error));
            backLeft.setPower((backLeftPower * PID.EncoderPID(rightEncoder, frontRight.getCurrentPosition(), KP, KI)) *//*+ (corrKP * error)*//*);
            frontRight.setPower((frontRightPower * PID.EncoderPID(rightEncoder, frontRight.getCurrentPosition(), KP, KI)) *//*+ (corrKP * error)*//*);
            backRight.setPower((backRightPower * PID.EncoderPID(rightEncoder, frontRight.getCurrentPosition(), KP, KI)) + (corrKP * error));

*//*
            frontRight.setPower(PID.EncoderPID(encoder, frontRight.getCurrentPosition(), KP, KI));
            frontLeft.setPower(-frontRight.getPower() + corrKP * error);
            backRight.setPower(frontRight.getPower());
            backLeft.setPower(-frontRight.getPower() + corrKP * error);
*//*

            if ((frontRight.getCurrentPosition() >= (rightEncoder - 50)) &&
                    (frontRight.getCurrentPosition() <= (rightEncoder + 50)) *//*&&
                (backLeft.getCurrentPosition() >= (leftEncoder - 50)) &&
                (backLeft.getCurrentPosition() <= (leftEncoder + 50))*//*) {
                stopState = (System.nanoTime() - startTime) / 1000000;
            } else {
                startTime = System.nanoTime();
            }
        }

    }*/

    void eReset() {

        SpeedControlledMotor[] motors = {frontLeft, backLeft, frontRight, backRight};

        for(SpeedControlledMotor motor: motors) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public boolean opModeIsActive() {return auto.getOpModeIsActive();}

}