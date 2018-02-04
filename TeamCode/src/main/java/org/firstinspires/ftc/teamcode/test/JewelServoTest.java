package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

@TeleOp(name="Jewel Servo Test")
//@Disabled
public class JewelServoTest extends OpMode {

    Hardware robot = new Hardware();

    public void init() {

        robot.init(hardwareMap);

        telemetry.addLine("Omit the first noun");
        telemetry.update();
    }

    public void loop() {
        if (gamepad1.a) {
            robot.jewelArms.turnWristCenter();
        } else if (gamepad1.x) {
            robot.jewelArms.turnWristLeft();
        } else if (gamepad1.b) {
            robot.jewelArms.turnWristRight();
        }
    }
}