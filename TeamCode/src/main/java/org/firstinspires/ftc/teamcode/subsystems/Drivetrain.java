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

    private double angle;
    private double[] speeds = new double[4];

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

        this.angle = angle;

        double speedMagnitude = Math.hypot(x, y); //Ankit is a homosexual
//agree- Ankit
/*
        double yComponent = angle == 0 || angle == Math.PI/2 || angle == Math.PI || angle == -Math.PI/2 ? (Math.sin(adjustedAngle)/Math.abs(Math.sin(adjustedAngle))) : Math.sin(adjustedAngle);
        double xComponent = angle == 0 || angle == Math.PI/2 || angle == Math.PI || angle == -Math.PI/2 ? (Math.cos(adjustedAngle)/Math.abs(Math.cos(adjustedAngle))) : Math.cos(adjustedAngle);
*/

        if (Math.abs(gamepadRightXRaw) > 0.00001) {
            desiredAngle = imu.getAngles()[0];
        }

        double angleCorrection = /*Math.abs(gamepadRightXRaw) < 0.00001 ? angularCorrectionPIDController.power(desiredAngle, imu.getAngles()[0]) : */0;

        double speeds[] = {Math.sin(adjustedAngle), Math.cos(adjustedAngle), Math.cos(adjustedAngle), Math.sin(adjustedAngle)};
        normalizeSpeeds(speeds);

        speeds[0] = (speeds[0] * speedMagnitude * TELEOP_SPEED_MULTIPLIER) + gamepadRightXRaw* TELEOP_SPEED_MULTIPLIER + angleCorrection;
        speeds[1] = (speeds[1] * speedMagnitude * TELEOP_SPEED_MULTIPLIER) + gamepadRightXRaw* TELEOP_SPEED_MULTIPLIER + angleCorrection;
        speeds[2] = (-speeds[2] * speedMagnitude * TELEOP_SPEED_MULTIPLIER) + gamepadRightXRaw* TELEOP_SPEED_MULTIPLIER + angleCorrection;
        speeds[3] = (-speeds[3] * speedMagnitude * TELEOP_SPEED_MULTIPLIER) + gamepadRightXRaw * TELEOP_SPEED_MULTIPLIER + angleCorrection;

        this.speeds = speeds;

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

//Everything below is retarded old stuff and needs to be fixed

    public void drive(/*int distance*//*Change to dirstance*/int ticks, double angle, double maxSpeedMultiplier) {

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
        angle = Math.toRadians(angle);
        double adjustedAngle = angle + Math.PI/4;

        frontLeftPower = maxSpeedMultiplier * AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * (Math.sin(adjustedAngle));
        backLeftPower = maxSpeedMultiplier * AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * (Math.cos(adjustedAngle));
        frontRightPower = maxSpeedMultiplier * AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * (Math.cos(adjustedAngle));
        backRightPower = maxSpeedMultiplier * AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * (Math.sin(adjustedAngle));

        while (opModeIsActive() && (stopState <= 1000)) {
            double PIDMultiplier = distancePIDController.power(-ticks, frontRight.getCurrentPosition());
            double angleCorrection = angularCorrectionPIDController.power(initialHeading, imu.getAngles()[0]);
            frontLeft.setPower(frontLeftPower * -PIDMultiplier + angleCorrection);
            backLeft.setPower(backLeftPower * -PIDMultiplier + angleCorrection);
            frontRight.setPower(frontRightPower * PIDMultiplier + angleCorrection);
            backRight.setPower(backRightPower * PIDMultiplier + angleCorrection);
            auto.telemetry.addData("Right Front Encoder", frontRight.getCurrentPosition());
            auto.telemetry.addData("StopState", stopState);
            auto.telemetry.update();


            if ((frontRight.getCurrentPosition() >= (-ticks - DISTANCE_TOLERANCE)) && (frontRight.getCurrentPosition() <= (-ticks + DISTANCE_TOLERANCE))) {
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
        while (opModeIsActive() && (stopState <= 1000)) {
            double power = angularTurnPIDController.power(angle, imu.getAngles()[0]);
            frontLeft.setPower(power);
            backLeft.setPower(power);
            frontRight.setPower(power);
            backRight.setPower(power);
            auto.telemetry.addData("Angle", imu.getAngles()[0]);
            auto.telemetry.addData("i", angularTurnPIDController.getI());
            auto.telemetry.update();


            if (imu.getAngles()[0] >= (angle - ANGLE_TOLERANCE) && imu.getAngles()[0] <= (angle + ANGLE_TOLERANCE)) {
                stopState = (System.nanoTime() - startTime) / 1000000;
            } else {
                startTime = System.nanoTime();
            }
        }

    }

    public int getPos() {
        return frontRight.getCurrentPosition();
    }

    public double getDriveAngle() {
        return angle;
    }

    public double getFrontLeftPower() {
        return speeds[0];
    }

    public double getBackLeftPower() {
        return speeds[1];
    }

    public double getFrontRightPower() {
        return speeds[2];
    }

    public double getBackRightPower() {
        return speeds[3];
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

        for (int i = 0; i < speeds.length; i++) {
            speeds[i] /= maxSpeed;
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