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
    boolean halfSpeed;
    BNO055_IMU imu;
    MaelstromAutonomous auto;

    public Drivetrain (Gamepad gamepad1, Hardware hardware, boolean halfSpeed) {
        this.gamepad1 = gamepad1;
        this.backLeft = hardware.backLeft;
        this.frontLeft = hardware.frontLeft;
        this.backRight = hardware.backRight;
        this.frontRight = hardware.frontRight;
        this.halfSpeed = halfSpeed;
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
        double frontLeft = -(Math.sin(angle) * speedMagnitude) + gamepad1.right_stick_x;
        double backLeft = -(Math.cos(angle) * speedMagnitude) + gamepad1.right_stick_x;
        double frontRight = (Math.cos(angle) * speedMagnitude) + gamepad1.right_stick_x;
        double backRight = (Math.sin(angle) * speedMagnitude) + gamepad1.right_stick_x;

        double driveScaleFactor = Math.abs(Math.max(
                Math.max(frontLeft, frontRight),
                Math.max(backLeft, backRight)))
                != 0 ? Math.abs(Math.max(
                Math.max(frontLeft, frontRight),
                Math.max(backLeft, backRight))) : 1
        ;


        frontLeft /= driveScaleFactor;
        frontRight /= driveScaleFactor;
        backLeft /= driveScaleFactor;
        backRight /= driveScaleFactor;

        if (!halfSpeed) {
            this.frontLeft.setSpeed(frontLeft);
            this.frontRight.setSpeed(backLeft);
            this.backLeft.setSpeed(frontRight);
            this.backRight.setSpeed(backRight);
        }
        else {
            this.frontLeft.setSpeed(0.5*frontLeft);
            this.frontRight.setSpeed(0.5*backLeft);
            this.backLeft.setSpeed(0.5*frontRight);
            this.backRight.setSpeed(0.5*backRight);
        }

    }




    public void drive(int rightEncoder





                      /*Change to dirstance*/



                      , double angle, double KP, double KI) {
        double frontLeft;
        double backLeft;
        double frontRight;
        double backRight;

        eReset();

        double error;
        long startTime = System.nanoTime();
        long stopState = 0;
        rightEncoder = -rightEncoder;
        PID.i = 0;
        double initialHeading = imu.getAngles()[0];
        double corrKP = 0.01;
        angle = angle * (Math.PI / 180);
        frontLeft = -(Math.sin(angle + (Math.PI / 4)));
        backLeft = -(Math.cos(angle + (Math.PI / 4)));
        frontRight = (Math.cos(angle + (Math.PI / 4)));
        backRight = (Math.sin(angle + (Math.PI / 4)));

        while (opModeIsActive() && (stopState <= 1000)) {
            error = imu.getAngles()[0] - initialHeading;
            this.frontLeft.setSpeed((frontLeft * PID.EncoderPID(rightEncoder, this.frontRight.getCurrentPosition(), KP, KI)) + (corrKP * error));
            this.backLeft.setSpeed((backLeft * PID.EncoderPID(rightEncoder, this.frontRight.getCurrentPosition(), KP, KI)) /*+ (corrKP * error)*/);
            this.frontRight.setSpeed((frontRight * PID.EncoderPID(rightEncoder, this.frontRight.getCurrentPosition(), KP, KI)) /*+ (corrKP * error)*/);
            this.backRight.setSpeed((backRight * PID.EncoderPID(rightEncoder, this.frontRight.getCurrentPosition(), KP, KI)) + (corrKP * error));

/*
            frontRight.setSpeed(PIDController.EncoderPID(encoder, frontRight.getCurrentPosition(), KP, KI));
            frontLeft.setSpeed(-frontRight.getPower() + corrKP * error);
            backRight.setSpeed(frontRight.getPower());
            backLeft.setSpeed(-frontRight.getPower() + corrKP * error);
*/

            if ((this.frontRight.getCurrentPosition() >= (rightEncoder - 50)) &&
                    (this.frontRight.getCurrentPosition() <= (rightEncoder + 50)) /*&&
                (backLeft.getCurrentPosition() >= (leftEncoder - 50)) &&
                (backLeft.getCurrentPosition() <= (leftEncoder + 50))*/) {
                stopState = (System.nanoTime() - startTime) / 1000000;
            } else {
                startTime = System.nanoTime();
            }
        }

    }

    void eReset() {

        DcMotor[] motors = {frontLeft, backLeft, frontRight, backRight};

        for(DcMotor motor: motors) {
            motor.setSpeed(0);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    public boolean opModeIsActive() {return auto.getOpModeIsActive();}

}