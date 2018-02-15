package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

@TeleOp(name="Sensor Output")
//@Disabled
public class SensorOutput extends LinearOpMode {

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addLine("Omit the first noun");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("Jewel sensor red", robot.jewelSensor.red());
            telemetry.addData("Jewel sensor blue", robot.jewelSensor.blue());

            telemetry.addData("Ultrasonic distance", robot.rangeSensor.getDistance(DistanceUnit.INCH));

            telemetry.addData("Right lift opto distance", robot.rightLiftDistance.getLightDetected());
            telemetry.addData("Left lift opto distance", robot.leftLiftDistance.getLightDetected());

            telemetry.addData("Right Front Encoder", robot.frontRight.getCurrentPosition());
            telemetry.addData("Left Front Encoder", robot.frontLeft.getCurrentPosition());
            telemetry.addData("Right Back Encoder", robot.backRight.getCurrentPosition());
            telemetry.addData("Left Back Encoder", robot.backLeft.getCurrentPosition());

/*
            telemetry.addData("Left Lift Encoder", robot.leftLift.getCurrentPosition());
            telemetry.addData("Right Lift Encoder", robot.rightLift.getCurrentPosition());
*/

            telemetry.addData("Relic Extender Encoder", robot.relicExtender.getCurrentPosition());
            telemetry.update();

            if (gamepad1.a) {
                robot.drivetrain.eReset();
            }
        }
    }
}