package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.control.AutonomousOpMode;
import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.control.PIDController;
import org.firstinspires.ftc.teamcode.control.SpeedControlledMotor;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.control.Utils;
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
    private AutonomousOpMode auto;
    private Hardware hardware;
    public Telemetry telemetry;

    private PIDController angularCorrectionPIDController = new PIDController(angleCorrectionKP, angleCorrectionKI, angleCorrectionKD, angleCorrectionMaxI);
    private PIDController angularTurnPIDController = new PIDController(angleTurnKP, angleTurnKI, angleTurnKD, angleTurnMaxI);
    private PIDController smallAngularTurnPIDController = new PIDController(smallAngleTurnKP, smallAngleTurnKI, smallAngleTurnKD, smallAngleTurnMaxI);
    private PIDController distancePIDController = new PIDController(distanceKP, distanceKI, distanceKD, distanceMaxI);
    private PIDController shortDistancePIDController = new PIDController(shortDistanceKP, shortDistanceKI, shortDistanceKD, shortDistanceMaxI);

    private double angle;
    private double[] speeds = new double[4];

    public Drivetrain (/*Gamepad gamepad1,*/ Hardware hardware/*, boolean halfSpeed*/) {
        //this.gamepad1 = gamepad1;
        this.hardware = hardware;
        this.backLeft = hardware.backLeft;
        this.frontLeft = hardware.frontLeft;
        this.backRight = hardware.backRight;
        this.frontRight = hardware.frontRight;
        this.imu = hardware.imu;
        /*this.halfSpeed = halfSpeed;*/
    }

    public void setAuto (AutonomousOpMode auto) {
        this.auto = auto;
        this.telemetry = auto.getTelemetry();
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

        Utils.normalizeValues(speeds);

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


    public void drive(/*int distance*//*Change to dirstance*/int ticks, double angle, double maxSpeed) {

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

        frontLeftPower = -AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.sin(adjustedAngle);
        backLeftPower = -AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.cos(adjustedAngle);
        frontRightPower = AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.cos(adjustedAngle);
        backRightPower = AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.sin(adjustedAngle);

        while (opModeIsActive() && (stopState <= 1000)) {
            double PIDMultiplier = Math.abs(ticks) < 250 ? shortDistancePIDController.power(-ticks, frontRight.getCurrentPosition())
                    : distancePIDController.power(-ticks, frontRight.getCurrentPosition());
            double angleCorrection = angularCorrectionPIDController.power(initialHeading, imu.getAngles()[0]);
            speeds[0] = frontLeftPower * PIDMultiplier;
            speeds[1] = backLeftPower * PIDMultiplier;
            speeds[2] = frontRightPower * PIDMultiplier;
            speeds[3] = backRightPower * PIDMultiplier;

            Utils.normalizeSpeedsToMax(speeds, maxSpeed);

            frontLeft.setPower(speeds[0] + angleCorrection);
            backLeft.setPower(speeds[1] + angleCorrection);
            frontRight.setPower(speeds[2] + angleCorrection);
            backRight.setPower(speeds[3] + angleCorrection);

            telemetry.addData("Right Front Encoder", frontRight.getCurrentPosition());
            telemetry.addData("StopState", stopState);
            telemetry.update();


            if (Math.abs(frontRight.getCurrentPosition() - -ticks) <= DISTANCE_TOLERANCE) {
                stopState = (System.nanoTime() - startTime) / 1000000;
            } else {
                startTime = System.nanoTime();
            }
        }

    }

    public void driveForTime(double speed, double angle, double time) {

        double frontLeftPower;
        double backLeftPower;
        double frontRightPower;
        double backRightPower;

        eReset();


        speed *= -1;
/*
        double ticks = distance/(Math.PI * WHEEL_DIAMETER) * NEVEREST_20_COUNTS_PER_REV;
*/
        long startTime = System.nanoTime();
        double elapsedTime = 0;
        double initialHeading = imu.getAngles()[0];
        angle = Math.toRadians(angle);
        double adjustedAngle = angle + Math.PI/4;

        frontLeftPower = -AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.sin(adjustedAngle);
        backLeftPower = -AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.cos(adjustedAngle);
        frontRightPower = AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.cos(adjustedAngle);
        backRightPower = AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.sin(adjustedAngle);

        while (opModeIsActive() && (elapsedTime < time)) {
            double angleCorrection = angularCorrectionPIDController.power(initialHeading, imu.getAngles()[0]);

            speeds[0] = frontLeftPower * speed + angleCorrection;
            speeds[1] = backLeftPower * speed + angleCorrection;
            speeds[2] = frontRightPower * speed + angleCorrection;
            speeds[3] = backRightPower * speed + angleCorrection;

            frontLeft.setPower(speeds[0]);
            backLeft.setPower(speeds[1]);
            frontRight.setPower(speeds[2]);
            backRight.setPower(speeds[3]);

            telemetry.addData("Right Front Encoder", frontRight.getCurrentPosition());
            telemetry.addData("StopState", elapsedTime);
            telemetry.update();

             elapsedTime = (System.nanoTime() - startTime) / 1e9;
        }

    }

    public void strafeTillColumn (RelicRecoveryVuMark column, double maxSpeedMultiplier, double angle) {

        double frontLeftPower;
        double backLeftPower;
        double frontRightPower;
        double backRightPower;

        int columnCount = 0;
        eReset();

        long startTime = System.nanoTime();
        long stopState = 0;

        double initialHeading = imu.getAngles()[0];
        angle = Math.toRadians(angle);
        double adjustedAngle = angle + Math.PI/4;

        frontLeftPower = -maxSpeedMultiplier * AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * (Math.sin(adjustedAngle));
        backLeftPower = -maxSpeedMultiplier * AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * (Math.cos(adjustedAngle));
        frontRightPower = maxSpeedMultiplier * AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * (Math.cos(adjustedAngle));
        backRightPower = maxSpeedMultiplier * AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * (Math.sin(adjustedAngle));

        double firstOptoReading = hardware.leftLiftDistance.getRawLightDetected();

        loopTillCryptobox:
        while (opModeIsActive()) {
            double angleCorrection = angularCorrectionPIDController.power(initialHeading, imu.getAngles()[0]);
            frontLeft.setPower(frontLeftPower + angleCorrection);
            backLeft.setPower(backLeftPower  + angleCorrection);
            frontRight.setPower(frontRightPower + angleCorrection);
            backRight.setPower(backRightPower + angleCorrection);

            switch (column) {
                case RIGHT:
                    if (columnCount == 1) {
                        break loopTillCryptobox;
                    }
                    break;
                case CENTER:
                    if (columnCount == 2) {
                        break loopTillCryptobox;
                    }
                    break;
                case LEFT:
                    if (columnCount == 3) {
                        break loopTillCryptobox;
                    }
                    break;
                case UNKNOWN:
                    telemetry.update();
                    break loopTillCryptobox;
            }

            if (hardware.leftLiftDistance.getRawLightDetected()/firstOptoReading > 1.3 && stopState > 750) {
                startTime = System.nanoTime();
                columnCount++;
            } else {
                stopState = (System.nanoTime() - startTime) / 1000000;
            }

            telemetry.addData("distance", hardware.leftLiftDistance.getRawLightDetected());
            telemetry.update();

            try {
                Thread.sleep(1);
            }
            catch (InterruptedException e) {}
        }

        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }

    public void turnAngle(double angle, double speedMultiplier) {

        eReset();
        long startTime = System.nanoTime();
        long stopState = 0;
        while (opModeIsActive() && (stopState <= 1000)) {
            double power = speedMultiplier * Math.abs(angle) < 50 ? smallAngularTurnPIDController.power(angle >= 180 && imu.getAngles()[0] < 0 ? angle - 360 :
                    angle, imu.getAngles()[0]) : angularTurnPIDController.power(angle >= 180 && imu.getAngles()[0] < 0 ? angle - 360 : angle, imu.getAngles()[0]);
            frontLeft.setPower(power);
            backLeft.setPower(power);
            frontRight.setPower(power);
            backRight.setPower(power);
            telemetry.addData("Angle", imu.getAngles()[0]);
            telemetry.addData("i", angularTurnPIDController.getI());
            telemetry.update();


            if (Math.abs(imu.getAngles()[0]  - (angle >= 180 && imu.getAngles()[0] < 0 ? angle - 360 : angle)) <= ANGLE_TOLERANCE) {
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