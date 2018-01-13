package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

@TeleOp(name="Color Sensor Test")
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

            telemetry.addData("Jewel sensor red", robot.jewelSensor.red());
            telemetry.addData("Jewel sensor blue", robot.jewelSensor.blue());

            telemetry.update();
        }
    }
}