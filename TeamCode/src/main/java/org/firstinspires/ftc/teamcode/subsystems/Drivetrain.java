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

    //Gamepad gamepad1;
    SpeedControlledMotor frontLeft, backLeft, frontRight, backRight;
    boolean halfSpeed = false;
    BNO055_IMU imu;
    MaelstromAutonomous auto;

    double teleopAngle;

    public Drivetrain (/*Gamepad gamepad1,*/ Hardware hardware/*, boolean halfSpeed*/) {
        //this.gamepad1 = gamepad1;
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

    public void drive(double gamepadLeftYRaw, double gamepadLeftXRaw, double gamepadRightXRaw) {

        double x = -gamepadLeftYRaw;
        double y = -gamepadLeftXRaw;
        double angle = Math.atan2(y, x);
        angle += Math.PI/4;

        this.teleopAngle = angle;

        double speedMagnitude = Math.hypot(x, y);
        double frontLeftPower = (Math.sin(angle) * speedMagnitude) + gamepadRightXRaw;
        double backLeftPower = (Math.cos(angle) * speedMagnitude) + gamepadRightXRaw;
        double frontRightPower = -(Math.cos(angle) * speedMagnitude) + gamepadRightXRaw;
        double backRightPower = -(Math.sin(angle) * speedMagnitude) + gamepadRightXRaw;

        double speeds[] = {frontLeftPower, backLeftPower, frontRightPower, backRightPower};
        normalizeSpeeds(speeds);

        if (!halfSpeed) {
            frontLeft.setPower(speeds[0]);
            backLeft.setPower(speeds[1]);
            frontRight.setPower(speeds[2]);
            backRight.setPower(speeds[3]);
        }
        else {
            frontLeft.setPower(0.5 * speeds[0]);
            backLeft.setPower(0.5 * speeds[1]);
            frontRight.setPower(0.5 * speeds[2]);
            backRight.setPower(0.5 * speeds[3]);
        }

    }

    public double getTeleopAngle() {
        return teleopAngle;
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

    public void normalizeSpeeds (double[] speeds) {

        double maxSpeed = 0;

        for (int i = 0; i < speeds.length; i++) {
            maxSpeed = Math.max(maxSpeed, Math.abs(speeds[i]));
        }
        if (maxSpeed > 1) {
            for (int i = 0; i < speeds.length; i++) {
                speeds[i] /= maxSpeed;
            }
        }
    }

    /*public double[] smoothSpeeds(double[] speeds) {
        for (int i = 0; i < speeds.length; i++) {
            if (speeds[i] < currRightStickX) {
                currRightStickX -= STEP_AMOUNT;
            } else if (speeds[i] > currRightStickX) {
                currRightStickX += STEP_AMOUNT;
            }
        }
    }*/

}