package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

@Autonomous(name="Color Sensor Test")
//@Disabled
public class ColorSensorTest extends LinearOpMode {

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addLine("Omit the first noun");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Left distance", robot.leftLiftDistance.getLightDetected());
            telemetry.addData("Right distance", robot.rightLiftDistance.getLightDetected());
            telemetry.update();
        }
    }
}