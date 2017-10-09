package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Ramsey on 10/5/2017.
 */

public class Drivetrain {


    public void drive() {
        x = gamepad1.left_stick_y;
        y = -gamepad1.left_stick_x;
        if (x != 0) {
            angle = Math.atan(y/x);
        }
        else {
            angle = 0;
        }
        if (x < 0 && y > 0) {
            angle = angle + Math.PI;
        }
        else if (x < 0 && y <= 0) {
            angle = angle + Math.PI;
        }
        else if (x > 0 && y < 0) {
            angle = angle + (2*Math.PI);
        }
        else if (x == 0 && y > 0 ) {
            angle = Math.PI/2;
        }
        else if (x == 0 && y < 0 ) {
            angle = (3 * Math.PI) / 2;
        }

        telemetry.addData("angle:", angle*180/Math.PI);

        speedMagnitude = Math.hypot(x, y);
        frontLeft = -(Math.sin(angle + (Math.PI/4))) * speedMagnitude + gamepad1.right_stick_x;
        backLeft = -(Math.cos(angle + (Math.PI/4))) * speedMagnitude + gamepad1.right_stick_x;
        frontRight = (Math.cos(angle + (Math.PI/4))) * speedMagnitude + gamepad1.right_stick_x;
        backRight = (Math.sin(angle + (Math.PI/4))) * speedMagnitude + gamepad1.right_stick_x;


        telemetry.addData("x" , gamepad1.right_stick_x);


        driveScaleFactor = Math.abs(Math.max(
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

        telemetry.addData("FrontLeft: ", frontLeft);
        telemetry.addData("FrontRight: ", frontRight);
        telemetry.addData("BackLeft: ", backLeft);
        telemetry.addData("BackRight: ", backRight);

        if (halfSpeed) {
            robot.frontLeftMotor.setPower(0.5*frontLeft);
            robot.backLeftMotor.setPower(0.5*backLeft);
            robot.frontRightMotor.setPower(0.5*frontRight);
            robot.backRightMotor.setPower(0.5*backRight);
        }
        else {
            robot.frontLeftMotor.setPower(frontLeft);
            robot.backLeftMotor.setPower(backLeft);
            robot.frontRightMotor.setPower(frontRight);
            robot.backRightMotor.setPower(backRight);
        }

    }




    DcMotor LFMotor,  LBMotor, RFMotor, RBMotor;

    public Drivetrain (DcMotor LFMotor, DcMotor LBMotor, DcMotor RFMotor, DcMotor RBMotor) {
        this.LFMotor = LFMotor;
        this.LBMotor = LBMotor;
        this.RFMotor = RFMotor;
        this.RBMotor = RBMotor;
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
