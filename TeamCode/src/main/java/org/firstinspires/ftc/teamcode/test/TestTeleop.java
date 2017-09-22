package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

@TeleOp(name = "Test Teleop")
public class TestTeleop extends OpMode{

    Hardware robot = new Hardware();
    public void init() {

        robot.init(hardwareMap);
    }

    public void loop() {
        robot.leftMotor.setPower(gamepad1.left_stick_y);
        robot.rightMotor.setPower(-gamepad1.right_stick_y);
    }
}
