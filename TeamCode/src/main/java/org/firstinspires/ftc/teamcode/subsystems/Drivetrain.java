package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.hardware.Hardware;


/**
 * Created by Ramsey on 10/5/2017.
 */

public class Drivetrain {

    Gamepad gamepad1;
    DcMotor FLMotor, BLMotor, FRMotor, BRMotor;
    boolean halfSpeed;

    public Drivetrain (Gamepad gamepad1, Hardware hardware, boolean halfSpeed) {
        this.gamepad1 = gamepad1;
        this.BLMotor = hardware.BLMotor;
        this.FLMotor = hardware.FLMotor;
        this.BRMotor = hardware.BRMotor;
        this.FRMotor = hardware.FRMotor;
        this.halfSpeed = halfSpeed;
    }

    public Drivetrain (Hardware hardware) {
        this.BLMotor = hardware.BLMotor;
        this.FLMotor = hardware.FLMotor;
        this.BRMotor = hardware.BRMotor;
        this.FRMotor = hardware.FRMotor;
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
            telemetry.addData("Integral:", PID.i);

            robot.frontLeftMotor.setPower((frontLeft * PID.EncoderPID(rightEncoder, robot.frontRightMotor.getCurrentPosition(), KP, KI)) + (corrKP * error));
            robot.backLeftMotor.setPower((backLeft * PID.EncoderPID(rightEncoder, robot.frontRightMotor.getCurrentPosition(), KP, KI)) /*+ (corrKP * error)*/);
            robot.frontRightMotor.setPower((frontRight * PID.EncoderPID(rightEncoder, robot.frontRightMotor.getCurrentPosition(), KP, KI)) /*+ (corrKP * error)*/);
            robot.backRightMotor.setPower((backRight * PID.EncoderPID(rightEncoder, robot.frontRightMotor.getCurrentPosition(), KP, KI)) + (corrKP * error));

/*
            robot.frontRightMotor.setPower(PID.EncoderPID(encoder, robot.frontRightMotor.getCurrentPosition(), KP, KI));
            robot.frontLeftMotor.setPower(-robot.frontRightMotor.getPower() + corrKP * error);
            robot.backRightMotor.setPower(robot.frontRightMotor.getPower());
            robot.backLeftMotor.setPower(-robot.frontRightMotor.getPower() + corrKP * error);
*/
            //telemetry.addData("Front Left Encoder",robot.frontLeftMotor.getCurrentPosition());
            //telemetry.addData("Back Left Encoder",robot.backLeftMotor.getCurrentPosition());
            telemetry.addData("Front Right Encoder", robot.frontRightMotor.getCurrentPosition());
            telemetry.addData("Back Left Encoder", robot.backLeftMotor.getCurrentPosition());
            //telemetry.addData("Back Right Encoder",robot.backRightMotor.getCurrentPosition());

            telemetry.addData("Front Right Power", robot.frontRightMotor.getPower());
            /*
            telemetry.addData("Back Right Power",robot.backRightMotor.getPower());
            telemetry.addData("Front Left Power",robot.frontLeftMotor.getPower());
            telemetry.addData("Back Left Power",robot.backLeftMotor.getPower());
            */
            //telemetry.addData("kI", KI);
            telemetry.addData("PID:", PID.EncoderPID(rightEncoder, robot.frontRightMotor.getCurrentPosition(), KP, KI));
            telemetry.addData("Error:", PID.tempError);
            telemetry.update();

            if ((robot.frontRightMotor.getCurrentPosition() >= (rightEncoder - 50)) &&
                    (robot.frontRightMotor.getCurrentPosition() <= (rightEncoder + 50)) /*&&
                (robot.backLeftMotor.getCurrentPosition() >= (leftEncoder - 50)) &&
                (robot.backLeftMotor.getCurrentPosition() <= (leftEncoder + 50))*/) {
                stopState = (System.nanoTime() - startTime) / 1000000;
            } else {
                startTime = System.nanoTime();
            }

            sleep(1);
        }
    }
}
