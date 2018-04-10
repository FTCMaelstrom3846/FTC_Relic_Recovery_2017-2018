package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.control.PIDController;
import org.firstinspires.ftc.teamcode.control.SpeedControlledMotor;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.teamcode.control.Utils;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.sensors.BNO055_IMU;
import org.firstinspires.ftc.teamcode.sensors.ExternalEncoder;
import org.firstinspires.ftc.teamcode.sensors.MaxbotixUltrasonicSensor;


/**
 * Created by Ramsey on 10/5/2017.
 */

public class Drivetrain implements Constants {

    private double desiredAngle = 0;
    //Gamepad gamepad1;
    private SpeedControlledMotor frontLeft, backLeft, frontRight, backRight;
    private ExternalEncoder xPos;
    private ExternalEncoder yPos;
    private boolean halfSpeed = false;
    private BNO055_IMU imu;
    private Utils.AutonomousOpMode auto;
    private Hardware hardware;
    private MaxbotixUltrasonicSensor rangeSensor;
    public Telemetry telemetry;

    private PIDController angularCorrectionPIDController = new PIDController(angleCorrectionKP, angleCorrectionKI, angleCorrectionKD, angleCorrectionMaxI);
    private PIDController angularTurnPIDController = new PIDController(angleTurnKP, angleTurnKI, angleTurnKD, angleTurnMaxI);
    private PIDController smallAngularTurnPIDController = new PIDController(smallAngleTurnKP, smallAngleTurnKI, smallAngleTurnKD, smallAngleTurnMaxI);
    private PIDController distancePIDController = new PIDController(distanceKP, distanceKI, distanceKD, distanceMaxI);
    //private PIDController ultrasonicDistancePIDController = new PIDController(ultrasonicDistanceKP, ultrasonicDistanceKI, ultrasonicDistanceKD, ultrasonicDistanceMaxI);
    private PIDController shortDistancePIDController = new PIDController(shortDistanceKP, shortDistanceKI, shortDistanceKD, shortDistanceMaxI);

    private double angle;
    private double[] speeds = new double[4];

    public Drivetrain (/*Gamepad gamepad1,*/ Hardware hardware/*, boolean halfSpeed*/) {
        //this.gamepad1 = gamepad1;
        this.hardware = hardware;
        backLeft = hardware.backLeft;
        frontLeft = hardware.frontLeft;
        backRight = hardware.backRight;
        frontRight = hardware.frontRight;
        rangeSensor = hardware.rangeSensor;
        xPos = hardware.xPos;
        yPos = hardware.yPos;
        imu = hardware.imu;
        /*this.halfSpeed = halfSpeed;*/
        auto = hardware.auto;
        telemetry = auto == null? null : auto.getTelemetry();
    }

    public void drive(double gamepadLeftYRaw, double gamepadLeftXRaw, double gamepadRightXRaw) {

        double x = -gamepadLeftYRaw;
        double y = gamepadLeftXRaw;
        double angle = Math.atan2(y, x);
        double adjustedAngle = angle + Math.PI / 4;

        this.angle = angle;

        double speedMagnitude = Math.hypot(x, y);

/*
        double yComponent = angle == 0 || angle == Math.PI/2 || angle == Math.PI || angle == -Math.PI/2 ? (Math.sin(adjustedAngle)/Math.abs(Math.sin(adjustedAngle))) : Math.sin(adjustedAngle);
        double xComponent = angle == 0 || angle == Math.PI/2 || angle == Math.PI || angle == -Math.PI/2 ? (Math.cos(adjustedAngle)/Math.abs(Math.cos(adjustedAngle))) : Math.cos(adjustedAngle);
*/

        if (Math.abs(gamepadRightXRaw) > 0.00001) {
            desiredAngle = imu.getRelativeYaw();
        }

        double angleCorrection = /*Math.abs(gamepadRightXRaw) < 0.00001 ? angularCorrectionPIDController.power(desiredAngle, imu.getRelativeYaw()) : */0;

        double speeds[] = {Math.sin(adjustedAngle), Math.cos(adjustedAngle), Math.cos(adjustedAngle), Math.sin(adjustedAngle)};

        Utils.normalizeValues(speeds);

        speeds[0] = (speeds[0] * speedMagnitude * TELEOP_SPEED_MULTIPLIER) + gamepadRightXRaw * TELEOP_TURNING_SPEED_MULTIPLIER + angleCorrection;
        speeds[1] = (speeds[1] * speedMagnitude * TELEOP_SPEED_MULTIPLIER) + gamepadRightXRaw * TELEOP_TURNING_SPEED_MULTIPLIER + angleCorrection;
        speeds[2] = (-speeds[2] * speedMagnitude * TELEOP_SPEED_MULTIPLIER) + gamepadRightXRaw * TELEOP_TURNING_SPEED_MULTIPLIER + angleCorrection;
        speeds[3] = (-speeds[3] * speedMagnitude * TELEOP_SPEED_MULTIPLIER) + gamepadRightXRaw * TELEOP_TURNING_SPEED_MULTIPLIER + angleCorrection;

        this.speeds = speeds;

        if (!halfSpeed) {
            frontLeft.setPower(speeds[0]);
            backLeft.setPower(speeds[1]);
            frontRight.setPower(speeds[2]);
            backRight.setPower(speeds[3]);
        } else {
            frontLeft.setPower(0.5 * speeds[0]);
            backLeft.setPower(0.5 * speeds[1]);
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
        double initialHeading = imu.getRelativeYaw();
        angle = Math.toRadians(angle);
        double adjustedAngle = angle + Math.PI/4;

        frontLeftPower = -AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.sin(adjustedAngle);
        backLeftPower = -AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.cos(adjustedAngle);
        frontRightPower = AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.cos(adjustedAngle);
        backRightPower = AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.sin(adjustedAngle);

        while (opModeIsActive() && (stopState <= 1000)) {
            double PIDMultiplier = Math.abs(ticks) <= 600 ? shortDistancePIDController.power(-ticks, frontRight.getCurrentPosition())
                    : distancePIDController.power(-ticks, frontRight.getCurrentPosition());
            double angleCorrection = angularCorrectionPIDController.power(initialHeading, imu.getRelativeYaw());
            speeds[0] = frontLeftPower * PIDMultiplier;
            speeds[1] = backLeftPower * PIDMultiplier;
            speeds[2] = frontRightPower * PIDMultiplier;
            speeds[3] = backRightPower * PIDMultiplier;
            frontRight.setPower(0.5 * speeds[2]);
            backRight.setPower(0.5 * speeds[3]);


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

/*
    public void driveTillUltrasonicDistance(*//*int distance*//**//*Change to dirstance*//*double distance, double angle, double maxSpeed) {

        double frontLeftPower;
        double backLeftPower;
        double frontRightPower;
        double backRightPower;

        eReset();

*//*
        double ticks = distance/(Math.PI * WHEEL_DIAMETER) * NEVEREST_20_COUNTS_PER_REV;
*//*
        long startTime = System.nanoTime();
        long stopState = 0;
        double initialHeading = imu.getRelativeYaw();
        angle = Math.toRadians(angle);
        double adjustedAngle = angle + Math.PI/4;

        frontLeftPower = AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.sin(adjustedAngle);
        backLeftPower = AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.cos(adjustedAngle);
        frontRightPower = -AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.cos(adjustedAngle);
        backRightPower = -AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.sin(adjustedAngle);

        while (opModeIsActive() && (stopState <= 1000)) {
            double PIDMultiplier = ultrasonicDistancePIDController.power(distance, hardware.rangeSensor.getDistance(DistanceUnit.INCH));
            double angleCorrection = angularCorrectionPIDController.power(initialHeading, imu.getRelativeYaw());
            speeds[0] = frontLeftPower * PIDMultiplier;
            speeds[1] = backLeftPower * PIDMultiplier;
            speeds[2] = frontRightPower * PIDMultiplier;
            speeds[3] = backRightPower * PIDMultiplier;

            Utils.normalizeSpeedsToMax(speeds, maxSpeed);

            frontLeft.setPower(speeds[0] + angleCorrection);
            backLeft.setPower(speeds[1] + angleCorrection);
            frontRight.setPower(speeds[2] + angleCorrection);
            backRight.setPower(speeds[3] + angleCorrection);

            telemetry.addData("Distance", hardware.rangeSensor.getDistance(DistanceUnit.INCH));
            telemetry.addData("Multiplier", PIDMultiplier);
            telemetry.addData("StopState", stopState);
            telemetry.update();


            if (Math.abs(hardware.rangeSensor.getDistance(DistanceUnit.INCH) - distance) <= ULTRASONIC_DISTANCE_TOLERANCE) {
                stopState = (System.nanoTime() - startTime) / 1000000;
            } else {
                startTime = System.nanoTime();
            }
        }

    }*/

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
        double initialHeading = imu.getRelativeYaw();
        angle = Math.toRadians(angle);
        double adjustedAngle = angle + Math.PI/4;

        frontLeftPower = -AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.sin(adjustedAngle);
        backLeftPower = -AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.cos(adjustedAngle);
        frontRightPower = AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.cos(adjustedAngle);
        backRightPower = AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.sin(adjustedAngle);

        while (opModeIsActive() && (elapsedTime < time)) {
            double angleCorrection = angularCorrectionPIDController.power(initialHeading, imu.getRelativeYaw());

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

        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }

    public void strafeTillColumn (/*RelicRecoveryVuMark column, */Utils.AutoColor color, double maxSpeedMultiplier, double angle) {

        OpticalDistanceSensor optoSensor = color == Utils.AutoColor.RED ? hardware.leftLiftDistance : hardware.rightLiftDistance;
        double frontLeftPower;
        double backLeftPower;
        double frontRightPower;
        double backRightPower;

        int columnCount = 0;
        eReset();

        long startTime = System.nanoTime();
        long stopState = 0;

        double initialHeading = imu.getRelativeYaw();
        angle = Math.toRadians(angle);
        double adjustedAngle = angle + Math.PI/4;

        frontLeftPower = -maxSpeedMultiplier * AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * (Math.sin(adjustedAngle));
        backLeftPower = -maxSpeedMultiplier * AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * (Math.cos(adjustedAngle));
        frontRightPower = maxSpeedMultiplier * AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * (Math.cos(adjustedAngle));
        backRightPower = maxSpeedMultiplier * AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * (Math.sin(adjustedAngle));

        double firstOptoReading = optoSensor.getRawLightDetected();

        loopTillCryptobox:
        while (opModeIsActive()) {
            double angleCorrection = angularCorrectionPIDController.power(initialHeading, imu.getRelativeYaw());
            frontLeft.setPower(frontLeftPower + angleCorrection);
            backLeft.setPower(backLeftPower  + angleCorrection);
            frontRight.setPower(frontRightPower + angleCorrection);
            backRight.setPower(backRightPower + angleCorrection);


            if (columnCount == 1) {
                break loopTillCryptobox;
            }
            /*if (color == Utils.AutoColor.RED) {
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
                        telemetry.addLine("Not detected");
                        telemetry.update();
                        if (columnCount == 1) {
                            break loopTillCryptobox;
                        }
                        break;
                }
            }

            if (color == Utils.AutoColor.BLUE) {
                switch (column) {
                    case LEFT:
                        if (columnCount == 1) {
                            break loopTillCryptobox;
                        }
                        break;
                    case CENTER:
                        if (columnCount == 2) {
                            break loopTillCryptobox;
                        }
                        break;
                    case RIGHT:
                        if (columnCount == 3) {
                            break loopTillCryptobox;
                        }
                        break;
                    case UNKNOWN:
                        telemetry.addLine("Not detected");
                        telemetry.update();
                        if (columnCount == 1) {
                            break loopTillCryptobox;
                        }
                        break;
                }
            }*/

            telemetry.addData("stopState", stopState);
            telemetry.addData("first/new opto", optoSensor.getRawLightDetected()/firstOptoReading);
            telemetry.addData("first opto", firstOptoReading);
            telemetry.addData("column count", columnCount);
            if (optoSensor.getRawLightDetected()/firstOptoReading > OPTOSENSOR_DETECT_CRYPTOBOX_THRESHOLD && stopState > (columnCount == 0 ? 0 : TIME_BETWEEN_OPTOSENSOR_DETECTIONS)) {
                startTime = System.nanoTime();
                columnCount++;
            } else {
                stopState = (System.nanoTime() - startTime) / 1000000;
            }

            //telemetry.addData("distance", optoSensor.getRawLightDetected());
            telemetry.update();

            if (stopState > 10000) {
                break;
            }
        }

        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }

    public void turnAngle(double angle, double speedMultiplier, double minPower) {

        eReset();
        long startTime = System.nanoTime();
        long stopState = 0;
        while (opModeIsActive() && (stopState <= 1000)) {

            double power = speedMultiplier * Math.abs(angle) < 50 ? smallAngularTurnPIDController.power(angle >= 180 && imu.getRelativeYaw() < 0 ? angle - 360 :
                    angle, imu.getRelativeYaw()) : angularTurnPIDController.power(angle >= 180 && imu.getRelativeYaw() < 0 ? angle - 360 : angle, imu.getRelativeYaw());

            if (Math.abs(imu.getRelativeYaw() - (angle >= 180 && imu.getRelativeYaw() < 0 ? angle - 360 : angle)) <= ANGLE_TOLERANCE) {

                stopState = (System.nanoTime() - startTime) / 1000000;
            } else {
                startTime = System.nanoTime();
                if (power < 0) {
                    power = Math.abs(power) < minPower ? -minPower : power;
                } else {
                    power = Math.abs(power) < minPower ? minPower : power;
                }
            }
            frontLeft.setPower(power);
            backLeft.setPower(power);
            frontRight.setPower(power);
            backRight.setPower(power);
            telemetry.addData("Angle", imu.getRelativeYaw());
            telemetry.addData("i", angularTurnPIDController.getI());
            telemetry.update();

        }

    }

    public void turnAngle(double angle, double speedMultiplier) {

        eReset();
        long startTime = System.nanoTime();
        long stopState = 0;
        while (opModeIsActive() && (stopState <= 1000)) {

            double power = speedMultiplier * Math.abs(angle) < 50 ? smallAngularTurnPIDController.power(angle >= 180 && imu.getRelativeYaw() < 0 ? angle - 360 :
                    angle, imu.getRelativeYaw()) : angularTurnPIDController.power(angle >= 180 && imu.getRelativeYaw() < 0 ? angle - 360 : angle, imu.getRelativeYaw());

            frontLeft.setPower(power);
            backLeft.setPower(power);
            frontRight.setPower(power);
            backRight.setPower(power);
            telemetry.addData("Angle", imu.getRelativeYaw());
            telemetry.addData("i", angularTurnPIDController.getI());
            telemetry.update();

            if (Math.abs(imu.getRelativeYaw() - (angle >= 180 && imu.getRelativeYaw() < 0 ? angle - 360 : angle)) <= ANGLE_TOLERANCE) {

                stopState = (System.nanoTime() - startTime) / 1000000;
            } else {
                startTime = System.nanoTime();
            }

        }

    }

    public void intakeFlipAndCryptoLineup(int ticks, double speed, double time) {
        driveForTime(speed, 0, time);
        drive(ticks + frontRight.getCurrentPosition(), 0, 1);
    }

    public void driveToPos(int x, int y, double maxSpeed) {

        x = (int)((x/(Math.PI*2))*E4T_COUNTS_PER_REV);
        y = (int)((y/(Math.PI*2))*E4T_COUNTS_PER_REV);

        double frontLeftPower;
        double backLeftPower;
        double frontRightPower;
        double backRightPower;

        eReset();
        xPos.reset();
        yPos.reset();

        long startTime = System.nanoTime();
        long stopState = 0;
        double initialHeading = imu.getRelativeYaw();
        double distance = Math.hypot(x, y);

        while (opModeIsActive() && (stopState <= 1000)) {
            angle = Math.atan2(x - xPos.getPosition(), y - yPos.getPosition());
            double adjustedAngle = angle + Math.PI/4;

            frontLeftPower = -AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.sin(adjustedAngle);
            backLeftPower = -AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.cos(adjustedAngle);
            frontRightPower = AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.cos(adjustedAngle);
            backRightPower = AUTONOMOUS_GLOBAL_SPEED_MULTIPLIER * Math.sin(adjustedAngle);
            double PIDMultiplier = Math.abs(distance) <= 3000 ? shortDistancePIDController.power(distance, Math.hypot(xPos.getPosition(), yPos.getPosition()))
                    : distancePIDController.power(distance, Math.hypot(xPos.getPosition(), yPos.getPosition()));
            double angleCorrection = angularCorrectionPIDController.power(initialHeading, imu.getRelativeYaw());
            speeds[0] = frontLeftPower * PIDMultiplier;
            speeds[1] = backLeftPower * PIDMultiplier;
            speeds[2] = frontRightPower * PIDMultiplier;
            speeds[3] = backRightPower * PIDMultiplier;

            Utils.normalizeSpeedsToMax(speeds, maxSpeed);

            frontLeft.setPower(speeds[0] + angleCorrection);
            backLeft.setPower(speeds[1] + angleCorrection);
            frontRight.setPower(speeds[2] + angleCorrection);
            backRight.setPower(speeds[3] + angleCorrection);

            telemetry.addData("X Position", xPos.getPosition());
            telemetry.addData("Y Position", yPos.getPosition());
            telemetry.addData("Distance", distance);
            telemetry.addData("StopState", stopState);
            telemetry.update();


            if (Math.abs(distance - Math.hypot(xPos.getPosition(), yPos.getPosition())) <= DISTANCE_TOLERANCE) {
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


    public void eReset() {

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