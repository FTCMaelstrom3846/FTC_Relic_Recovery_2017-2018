package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.MinibotHardware;

@TeleOp(name = "Test Teleop")
public class MinibotTeleop extends OpMode {

    MinibotHardware robot = new MinibotHardware();
    public void init() {

        robot.init(hardwareMap);
    }

    public void loop() {
        robot.leftMotor.setPower(gamepad1.left_stick_y);
        robot.rightMotor.setPower(-gamepad1.right_stick_y);

        if (gamepad1.left_bumper) {
            robot.rightGripper.setPosition(0.25);
            robot.leftGripper.setPosition(0.75);
        }

        if (gamepad1.right_bumper) {
            robot.rightGripper.setPosition(0.5);
            robot.leftGripper.setPosition(0.5);
        }

    }
}
