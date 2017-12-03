package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.control.SpeedControlledMotor;
import org.firstinspires.ftc.teamcode.sensors.BNO055_IMU;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.RelicGrabber;

/**
 * Created by Ramsey on 10/15/2016.
 */

public class Hardware implements Constants {

    public BNO055_IMU imu;

    public SpeedControlledMotor frontLeft = new SpeedControlledMotor(frontLeftKP, frontLeftKI, frontLeftKD, frontLeftMaxI),
            frontRight = new SpeedControlledMotor(frontRightKP, frontRightKI, frontRightKD, frontRightMaxI),
            backLeft = new SpeedControlledMotor(backLeftKP, backLeftKI, backLeftKD, backLeftMaxI),
            backRight = new SpeedControlledMotor(backRightKP, backRightKI, backRightKD, backRightMaxI),
            leftLift = new SpeedControlledMotor(leftLiftKP, leftLiftKI, leftLiftKD, leftLiftMaxI),
            rightLift = new SpeedControlledMotor(rightLiftKP, rightLiftKI, rightLiftKD, rightLiftMaxI),
            relicExtender = new SpeedControlledMotor(relicExtenderKP, relicExtenderKI, relicExtenderKD, relicExtenderMaxI);

    public SpeedControlledMotor[] drivetrainMotors = {frontLeft, backLeft, frontRight, backRight};

    public DcMotor intake;

    public Servo relicWrist;
    public Servo relicGrabber;

    public CRServo conveyorTopLeft, conveyorTopRight, conveyorBottomRight, conveyorBottomLeft;

    public CRServo[] conveyorServos = new CRServo[4];

    public Drivetrain drivetrain;

    public Intake intakeSystem;

    public Lift lift;

    public RelicGrabber relicGrabberSystem;

    HardwareMap hwMap;

    public void init(HardwareMap hardwareMap) {

        this.hwMap = hardwareMap;

        imu = new BNO055_IMU("imu", hwMap);

        frontLeft.init(hwMap, "frontLeft");
        frontRight.init(hwMap, "frontRight");
        backLeft.init(hwMap, "backLeft");
        backRight.init(hwMap, "backRight");
        leftLift.init (hwMap, "leftLift");
        rightLift.init(hwMap, "rightLift");
        relicExtender.init(hwMap, "relicExtender");


        intake = hwMap.dcMotor.get("intake");

        relicWrist = hwMap.servo.get("relicWrist");
        relicGrabber = hwMap.servo.get("relicGrabber");

        conveyorTopRight = hwMap.crservo.get("conveyorTopRight");
        conveyorTopLeft = hwMap.crservo.get("conveyorTopLeft");
        conveyorBottomRight = hwMap.crservo.get("conveyorBottomRight");
        conveyorBottomLeft = hwMap.crservo.get("conveyorBottomLeft");

        conveyorBottomRight.setDirection(DcMotorSimple.Direction.REVERSE);
        conveyorTopLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        conveyorServos[0] = conveyorBottomLeft;
        conveyorServos[1] = conveyorBottomRight;
        conveyorServos[2] = conveyorTopLeft;
        conveyorServos[3] = conveyorTopRight;

        for(SpeedControlledMotor motor: drivetrainMotors) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        drivetrain = new Drivetrain(/*gamepad1,*/ this);

        intakeSystem = new Intake(this);

        lift = new Lift(this);

        relicGrabberSystem = new RelicGrabber(this);
    }

}