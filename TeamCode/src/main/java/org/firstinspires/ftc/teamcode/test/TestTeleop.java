package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

@TeleOp(name = "MiniBot Teleop")
//@Disabled
public class TestTeleop extends OpMode {

    Hardware robot = new Hardware();
    boolean dPadUpPreviousState = false;
    boolean dPadDownPreviousState = false;
    double speed;

    public void init() {

        robot.init(hardwareMap);

    }


    public void loop() {
        double power = Math.pow(gamepad1.right_stick_y, 1.8);
        robot.frontLeft.setPower(power);

        if (!gamepad1.dpad_up && dPadUpPreviousState) {
            speed += .1;
        } else {
            dPadUpPreviousState = gamepad1.a;
        }

        if (!gamepad1.dpad_down && dPadDownPreviousState) {
            speed -= .1;
        } else {
            dPadDownPreviousState = gamepad1.a;
        }

        telemetry.addData("Controller Raw", gamepad1.right_stick_y);
        telemetry.addData("Controller Adjusted", power);
        telemetry.addData("Speed", speed);
        telemetry.addData("RPM", robot.frontLeft.getRPM());
    }
}
