package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.MinibotHardware;

@TeleOp(name = "MiniBot Teleop")
@Disabled
public class MinibotTeleop extends OpMode {

    MinibotHardware robot = new MinibotHardware();
    public void init() {

        robot.init(hardwareMap);

        robot.relicGrabber.setPosition(.25);
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

        if (gamepad1.right_trigger > 0) {
            robot.lift.setPower(1);
        } else if (gamepad1.left_trigger > 0) {
            robot.lift.setPower(-1);
        } else {
            robot.lift.setPower(0);
        }

        if (gamepad1.dpad_up) {
            robot.relicWrist.setPosition(.5);
        } else if (gamepad1.dpad_down) {
            robot.relicWrist.setPosition(0);
        }

        if (gamepad1.dpad_right) {
            robot.relicGrabber.setPosition(.8);
        } else if (gamepad1.dpad_left) {
            robot.relicGrabber.setPosition(.25);
        }

    }
}
