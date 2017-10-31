package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.control.SpeedControlledMotor;
import org.firstinspires.ftc.teamcode.sensors.BNO055_IMU;
/*
import org.firstinspires.ftc.teamcode.subsystems.Conveyor;
*/
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

/**
 * Created by Ramsey on 10/15/2016.
 */

public class Hardware implements Constants {

    public BNO055_IMU imu;

    public SpeedControlledMotor frontLeft = new SpeedControlledMotor(frontLeftKP, frontLeftKI, frontLeftKD, frontLeftMaxI),
            frontRight = new SpeedControlledMotor(frontRightKP, frontRightKI, frontRightKD, frontRightMaxI),
            backLeft = new SpeedControlledMotor(backLeftKP, backLeftKI, backLeftKD, backLeftMaxI),
            backRight = new SpeedControlledMotor(backRightKP, backRightKI, backRightKD, backRightMaxI);

    public SpeedControlledMotor[] drivetrainMotors = {frontLeft, backLeft, frontRight, backRight};

    /*public DcMotor intake;
    public DcMotor leftLift;
    public DcMotor rightLift;
    public DcMotor relicExtender;

    public Servo relicWrist;
    public Servo relicGrabber;*/

/*    public CRServo conveyorTopRight;
    public CRServo conveyorTopLeft;*/
    public CRServo conveyorBottomRight;
    public CRServo conveyorBottomLeft;

/*
    public CRServo[] conveyorServos = {*/
/*conveyorTopRight, conveyorTopLeft,*//*
 conveyorBottomRight, conveyorBottomLeft};
*/

    public Drivetrain drivetrain = new Drivetrain(/*gamepad1,*/ this);

/*
    public Conveyor conveyor = new Conveyor(this);
*/



    HardwareMap hwMap;

    public void init(HardwareMap hardwareMap) {

        this.hwMap = hardwareMap;

        imu = new BNO055_IMU("imu", hwMap);

        frontLeft.init(hwMap, "frontLeft");
        frontRight.init(hwMap, "frontRight");
        backLeft.init(hwMap, "backLeft");
        backRight.init(hwMap, "backRight");
/*
        intake = hwMap.dcMotor.get("intake");
        leftLift = hwMap.dcMotor.get("leftLift");
        rightLift = hwMap.dcMotor.get("rightLift");
        relicExtender = hwMap.dcMotor.get("relicExtender");


        relicWrist = hwMap.servo.get("relicWrist");
        relicGrabber = hwMap.servo.get("relicGrabber");*/

        /*conveyorTopRight = hwMap.crservo.get("conveyorTopRight");
        conveyorTopLeft = hwMap.crservo.get("conveyorTopLeft");*/
        conveyorBottomRight = hwMap.crservo.get("conveyorBottomRight");
        conveyorBottomLeft = hwMap.crservo.get("conveyorBottomLeft");

/*
        conveyorTopRight.setDirection(DcMotorSimple.Direction.REVERSE);
*/
        conveyorBottomRight.setDirection(DcMotorSimple.Direction.REVERSE);

        for(SpeedControlledMotor motor: drivetrainMotors) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        /*for(CRServo servo: conveyorServos) {
            servo.setPower(0);
        }*/
    }

}