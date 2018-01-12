package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

@TeleOp(name="Optical Distance Sensor Test")
//@Disabled
public class OpticalDistanceSensorTest extends LinearOpMode {

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addLine("Omit the first noun");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                robot.rightLiftDistance.enableLed(false);
                robot.leftLiftDistance.enableLed(false);
            }

            telemetry.addData("Left light detected", robot.leftLiftDistance.getLightDetected());
            telemetry.addData("Left raw", robot.leftLiftDistance.getRawLightDetected());
            telemetry.addData("Right light detected", robot.rightLiftDistance.getLightDetected());
            telemetry.addData("Right raw", robot.rightLiftDistance.getRawLightDetected());
            telemetry.update();
        }
    }
}