package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

@TeleOp(name = "Test Teleop")
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
        double power = gamepad1.right_stick_x > 0 ? Math.pow(gamepad1.right_stick_x, 1.8): -Math.pow(-gamepad1.right_stick_x, 1.8);
        robot.frontLeft.setSpeed(power);

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

        telemetry.addData("Controller Raw", gamepad1.right_stick_x);
        telemetry.addData("Controller Adjusted", power);
        telemetry.addData("Speed", speed);
        telemetry.addData("Desired RPM", power*160);
        telemetry.addData("RPM", robot.frontLeft.getRPM());
    }
}
