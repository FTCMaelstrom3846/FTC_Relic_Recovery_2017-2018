package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.control.PIDController;
import org.firstinspires.ftc.teamcode.control.SpeedControlledMotor;
import org.firstinspires.ftc.teamcode.opModes.MaelstromAutonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.sensors.BNO055_IMU;


/**
 * Created by Ramsey on 10/5/2017.
 */

public class Drivetrain implements Constants {

    private double desiredAngle = 0;
    //Gamepad gamepad1;
    private SpeedControlledMotor frontLeft, backLeft, frontRight, backRight;
    private boolean halfSpeed = false;
    private BNO055_IMU imu;
    private MaelstromAutonomous auto;
    private Hardware hardware;

    private PIDController angularCorrectionPIDController = new PIDController(angleCorrectionKP, angleCorrectionKI, angleCorrectionKD, angleCorrectionMaxI);
    private PIDController angularTurnPIDController = new PIDController(angleTurnKP, angleTurnKI, angleTurnKD, angleTurnMaxI);
    private PIDController distancePIDController = new PIDController(distanceKP, distanceKI, distanceKD, distanceMaxI);


    double teleopAngle;
    public double speed1;
    public double speed2;
    public double speed3;
    public double speed4;

    public Drivetrain (/*Gamepad gamepad1,*/ Hardware hardware/*, boolean halfSpeed*/) {
        //this.gamepad1 = gamepad1;
        this.backLeft = hardware.backLeft;
        this.frontLeft = hardware.frontLeft;
        this.backRight = hardware.backRight;
        this.frontRight = hardware.frontRight;
        this.imu = hardware.imu;
        this.hardware = hardware;
        /*this.halfSpeed = halfSpeed;*/
    }

    public void setAuto (MaelstromAutonomous auto) {
        this.auto = auto;
    }

    public void drive(double gamepadLeftYRaw, double gamepadLeftXRaw, double gamepadRightXRaw) {

        double x = -gamepadLeftYRaw;
        double y = gamepadLeftXRaw;
        double angle = Math.atan2(y, x);
        double adjustedAngle = angle + Math.PI/4;

        this.teleopAngle = angle;

        double speedMagnitude = Math.hypot(x, y);

/*
        double yComponent = angle == 0 || angle == Math.PI/2 || angle == Math.PI || angle == -Math.PI/2 ? (Math.sin(adjustedAngle)/Math.abs(Math.sin(adjustedAngle))) : Math.sin(adjustedAngle);
        double xComponent = angle == 0 || angle == Math.PI/2 || angle == Math.PI || angle == -Math.PI/2 ? (Math.cos(adjustedAngle)/Math.abs(Math.cos(adjustedAngle))) : Math.cos(adjustedAngle);
*/

        if (Math.abs(gamepadRightXRaw) > 0.00001) {
            desiredAngle = imu.getAngles()[0];
        }

        double angleCorrection = /*Math.abs(gamepadRightXRaw) < 0.00001 ? angularCorrectionPIDController.power(desiredAngle, imu.getAngles()[0]) : */0;

        double frontLeftPower = (Math.sin(adjustedAngle) * speedMagnitude * TELEOP_SPEED_MULTIPLIER) + gamepadRightXRaw* TELEOP_SPEED_MULTIPLIER + angleCorrection;
        double backLeftPower = (Math.cos(adjustedAngle) * speedMagnitude * TELEOP_SPEED_MULTIPLIER) + gamepadRightXRaw* TELEOP_SPEED_MULTIPLIER + angleCorrection;
        double frontRightPower = -(Math.cos(adjustedAngle) * speedMagnitude * TELEOP_SPEED_MULTIPLIER) + gamepadRightXRaw* TELEOP_SPEED_MULTIPLIER + angleCorrection;
        double backRightPower = -(Math.sin(adjustedAngle) * speedMagnitude * TELEOP_SPEED_MULTIPLIER) + gamepadRightXRaw * TELEOP_SPEED_MULTIPLIER + angleCorrection;

        double speeds[] = {frontLeftPower, backLeftPower, frontRightPower, backRightPower};
        normalizeSpeeds(speeds);

        speed1 = speeds[0];
        speed2 = speeds[1];
        speed3 = speeds[2];
        speed4 = speeds[3];

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

    public void drive(/*int distance*//*Change to dirstance*/int ticks, double angle) {

        double frontLeftPower;
        double backLeftPower;
        double frontRightPower;
        double backRightPower;

        eReset();

/*
        double ticks = distance/(Math.PI * WHEEL_DIAMETER) * NEVEREST_20_COUNTS_PER_REV;
*/
        long startTime = System.nanoTime();
        long stopState = 0;
        double initialHeading = imu.getAngles()[0];
        angle *= (Math.PI / 180);
        frontLeftPower = AUTONOMOUS_SPEED_MULTIPLIER * (Math.sin(angle + (Math.PI / 4)));
        backLeftPower = AUTONOMOUS_SPEED_MULTIPLIER * (Math.cos(angle + (Math.PI / 4)));
        frontRightPower = AUTONOMOUS_SPEED_MULTIPLIER * (Math.cos(angle + (Math.PI / 4)));
        backRightPower = AUTONOMOUS_SPEED_MULTIPLIER * (Math.sin(angle + (Math.PI / 4)));

        while (opModeIsActive() /*&& (stopState <= 1000)*/) {
            double PIDMultiplier = distancePIDController.power(-ticks, frontRight.getCurrentPosition());
            double angleCorrection = angularCorrectionPIDController.power(initialHeading, imu.getAngles()[0]);
            frontLeft.setPower(frontLeftPower * -PIDMultiplier + angleCorrection);
            backLeft.setPower(backLeftPower * -PIDMultiplier + angleCorrection);
            frontRight.setPower(frontRightPower * PIDMultiplier + angleCorrection);
            backRight.setPower(backRightPower * PIDMultiplier + angleCorrection);
            auto.telemetry.addData("Right Front Encoder", frontRight.getCurrentPosition());
            auto.telemetry.addData("Left Front Encoder", frontLeft.getCurrentPosition());
            auto.telemetry.addData("Right Back Encoder", backRight.getCurrentPosition());
            auto.telemetry.addData("Left Back Encoder", backLeft.getCurrentPosition());
            auto.telemetry.update();


            if ((frontRight.getCurrentPosition() >= (ticks - 50)) &&
                    (frontRight.getCurrentPosition() <= (ticks + 50)) &&
                (backLeft.getCurrentPosition() >= (ticks - 50)) &&
                (backLeft.getCurrentPosition() <= (ticks + 50))) {
                stopState = (System.nanoTime() - startTime) / 1000000;
            } else {
                startTime = System.nanoTime();
            }
        }

    }

    public void turnAngle(double angle) {

        eReset();


        long startTime = System.nanoTime();
        long stopState = 0;
        while (opModeIsActive() /*&& (stopState <= 1000)*/) {
            double power = angularTurnPIDController.power(angle, imu.getAngles()[0]);
            frontLeft.setPower(power);
            backLeft.setPower(power);
            frontRight.setPower(power);
            backRight.setPower(power);
            auto.telemetry.addData("Angle", imu.getAngles()[0]);

            auto.telemetry.update();


            if (imu.getAngles()[0] >= (angle - 0.5) && frontRight.getCurrentPosition() <= (angle + 0.5)) {
                stopState = (System.nanoTime() - startTime) / 1000000;
            } else {
                startTime = System.nanoTime();
            }
        }

    }

    public int getPos() {
        return frontRight.getCurrentPosition();
    }


    void eReset() {


        for(SpeedControlledMotor motor: hardware.drivetrainMotors) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public boolean opModeIsActive() {
        return auto.getOpModeIsActive();
    }

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