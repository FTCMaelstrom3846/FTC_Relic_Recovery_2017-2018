package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.control.Constants;
import org.firstinspires.ftc.teamcode.control.SpeedControlledMotor;
import org.firstinspires.ftc.teamcode.control.Utils;
import org.firstinspires.ftc.teamcode.sensors.BNO055_IMU;
import org.firstinspires.ftc.teamcode.sensors.MaxbotixUltrasonicSensor;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.DumpPan;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.JewelArms;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.RelicGrabber;

/**
 * Created by Ramsey on 10/15/2016.
 */

public class Hardware implements Constants {

    public Utils.AutonomousOpMode auto;

    public BNO055_IMU imu;

    public SpeedControlledMotor frontLeft = new SpeedControlledMotor(frontLeftKP, frontLeftKI, frontLeftKD, frontLeftMaxI),
            frontRight = new SpeedControlledMotor(frontRightKP, frontRightKI, frontRightKD, frontRightMaxI),
            backLeft = new SpeedControlledMotor(backLeftKP, backLeftKI, backLeftKD, backLeftMaxI),
            backRight = new SpeedControlledMotor(backRightKP, backRightKI, backRightKD, backRightMaxI),
            /*leftLift = new SpeedControlledMotor(leftLiftKP, leftLiftKI, leftLiftKD, leftLiftMaxI),
            rightLift = new SpeedControlledMotor(rightLiftKP, rightLiftKI, rightLiftKD, rightLiftMaxI),*/
            relicExtender = new SpeedControlledMotor(relicExtenderKP, relicExtenderKI, relicExtenderKD, relicExtenderMaxI);

    public SpeedControlledMotor[] drivetrainMotors = {frontLeft, backLeft, frontRight, backRight};

    public DcMotor rightIntake;
    public DcMotor leftIntake;

    public Servo relicWrist, relicGrabber, dumpRight, dumpLeft,
            leftJewelArm, /*rightJewelArm*/ jewelWrist, panGripper;

    public CRServo leftLift;
    public CRServo rightLift;

    public ColorSensor jewelSensor;

    public Drivetrain drivetrain;

    public Intake intakeSystem;

    public Lift lift;

    public RelicGrabber relicGrabberSystem;

    public DumpPan dumpPan;

    public JewelArms jewelArms;

    public OpticalDistanceSensor rightLiftDistance, leftLiftDistance;

    public MaxbotixUltrasonicSensor rangeSensor;

    HardwareMap hwMap;

    public void init(HardwareMap hardwareMap) {

        this.hwMap = hardwareMap;

        imu = new BNO055_IMU("imu", this);

        frontLeft.init(hwMap, "frontLeft");
        frontRight.init(hwMap, "frontRight");
        backLeft.init(hwMap, "backLeft");
        backRight.init(hwMap, "backRight");
/*        leftLift.init (hwMap, "leftLift");
        rightLift.init(hwMap, "rightLift");*/
        relicExtender.init(hwMap, "relicExtender");

        rightIntake = hwMap.dcMotor.get("rightIntake");
        leftIntake = hwMap.dcMotor.get("leftIntake");

        relicWrist = hwMap.servo.get("relicWrist");
        relicGrabber = hwMap.servo.get("relicGrabber");
        dumpRight = hwMap.servo.get("dumpRight");
        dumpLeft = hwMap.servo.get("dumpLeft");

        panGripper = hwMap.servo.get("panGripper");

        leftLift = hwMap.crservo.get("leftLift");
        rightLift = hwMap.crservo.get("rightLift");

        leftJewelArm = hwMap.servo.get("leftJewelArm");
        //rightJewelArm = hwMap.servo.get("rightJewelArm");
        jewelWrist = hwMap.servo.get("jewelWrist");

        jewelSensor = hwMap.colorSensor.get("jewelSensor");

        rangeSensor = new MaxbotixUltrasonicSensor(hardwareMap.analogInput.get("rangeSensor"));

        drivetrain = new Drivetrain(/*gamepad1,*/ this);

        intakeSystem = new Intake(this);

        lift = new Lift(this);

        relicGrabberSystem = new RelicGrabber(this);

        dumpPan = new DumpPan(this);

        jewelArms = new JewelArms(this);

        rightLiftDistance = hwMap.opticalDistanceSensor.get("rightLiftDistance");
        leftLiftDistance = hwMap.opticalDistanceSensor.get("leftLiftDistance");

        for(SpeedControlledMotor motor: drivetrainMotors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //Change back to BREAK
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        dumpLeft.setDirection(Servo.Direction.REVERSE);
        rightIntake.setDirection(DcMotor.Direction.REVERSE);
        rightLift.setDirection(CRServo.Direction.REVERSE);

        /*leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);*/

        relicExtender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        jewelArms.raiseLeft();
        //jewelArms.raiseRight();

        relicGrabberSystem.resetWrist();

        relicGrabberSystem.openGrabber();

        dumpPan.lowerPan();
    }

    public void setAuto (Utils.AutonomousOpMode auto) {
        this.auto = auto;
    }

    public HardwareMap getHwMap() {
        return hwMap;
    }

}