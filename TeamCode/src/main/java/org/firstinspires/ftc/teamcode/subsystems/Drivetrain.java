package org.firstinspires.ftc.teamcode.subsystems;

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
    DcMotor FLMotor, BLMotor, FRMotor, BRMotor;
    boolean halfSpeed;
    BNO055_IMU imu;
    MaelstromAutonomous auto;

    public Drivetrain (Gamepad gamepad1, Hardware hardware, boolean halfSpeed) {
        this.gamepad1 = gamepad1;
        this.BLMotor = hardware.BLMotor;
        this.FLMotor = hardware.FLMotor;
        this.BRMotor = hardware.BRMotor;
        this.FRMotor = hardware.FRMotor;
        this.halfSpeed = halfSpeed;
    }

    public Drivetrain (Hardware hardware, BNO055_IMU imu, MaelstromAutonomous auto) {
        this.BLMotor = hardware.BLMotor;
        this.FLMotor = hardware.FLMotor;
        this.BRMotor = hardware.BRMotor;
        this.FRMotor = hardware.FRMotor;
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
            FLMotor.setPower(frontLeft);
            FRMotor.setPower(backLeft);
            BLMotor.setPower(frontRight);
            BRMotor.setPower(backRight);
        }
        else {
            FLMotor.setPower(0.5*frontLeft);
            FRMotor.setPower(0.5*backLeft);
            BLMotor.setPower(0.5*frontRight);
            BRMotor.setPower(0.5*backRight);
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
            FLMotor.setPower((frontLeft * PID.EncoderPID(rightEncoder, FRMotor.getCurrentPosition(), KP, KI)) + (corrKP * error));
            BLMotor.setPower((backLeft * PID.EncoderPID(rightEncoder, FRMotor.getCurrentPosition(), KP, KI)) /*+ (corrKP * error)*/);
            FRMotor.setPower((frontRight * PID.EncoderPID(rightEncoder, FRMotor.getCurrentPosition(), KP, KI)) /*+ (corrKP * error)*/);
            BRMotor.setPower((backRight * PID.EncoderPID(rightEncoder, FRMotor.getCurrentPosition(), KP, KI)) + (corrKP * error));

/*
            FRMotor.setPower(PID.EncoderPID(encoder, FRMotor.getCurrentPosition(), KP, KI));
            FLMotor.setPower(-FRMotor.getPower() + corrKP * error);
            BRMotor.setPower(FRMotor.getPower());
            BLMotor.setPower(-FRMotor.getPower() + corrKP * error);
*/

            if ((FRMotor.getCurrentPosition() >= (rightEncoder - 50)) &&
                    (FRMotor.getCurrentPosition() <= (rightEncoder + 50)) /*&&
                (BLMotor.getCurrentPosition() >= (leftEncoder - 50)) &&
                (BLMotor.getCurrentPosition() <= (leftEncoder + 50))*/) {
                stopState = (System.nanoTime() - startTime) / 1000000;
            } else {
                startTime = System.nanoTime();
            }
        }

    }

    void eReset() {

        FLMotor.setPower(0);
        BLMotor.setPower(0);
        FRMotor.setPower(0);
        BRMotor.setPower(0);

        FRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public boolean opModeIsActive() {return auto.getOpModeIsActive();}

}