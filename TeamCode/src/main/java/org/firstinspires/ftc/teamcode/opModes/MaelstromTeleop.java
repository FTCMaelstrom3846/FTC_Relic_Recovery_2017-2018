package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//import org.firstinspires.ftc.teamcode.control.GamepadInputFilter;
import org.firstinspires.ftc.teamcode.hardware.Hardware;


@TeleOp(name= "Telop")
//@Disabled
public class MaelstromTeleop extends OpMode {

    Hardware robot = new Hardware();
    //GamepadInputFilter gamepadFilter = new GamepadInputFilter();


    public void init() {

        robot.init(hardwareMap);

        telemetry.addLine("Omit the first noun");
        telemetry.update();
    }

    public void loop() {
        robot.drivetrain.drive(/*gamepadFilter.lazyLeftStickY*/(gamepad1.left_stick_y),
                /*gamepadFilter.lazyLeftStickX*/(gamepad1.left_stick_x), /*gamepadFilter.lazyRighStickX*/(gamepad1.right_stick_x));
        telemetry.addData("angle", robot.drivetrain.getTeleopAngle()*180/Math.PI);

        telemetry.addData("speed 1", robot.drivetrain.speed1);
        telemetry.addData("speed 2", robot.drivetrain.speed2);
        telemetry.addData("speed 3", robot.drivetrain.speed3);
        telemetry.addData("speed 4", robot.drivetrain.speed4);

        telemetry.addData("Front left RPM:", robot.frontLeft.getRPM());
        telemetry.addData("Front right RPM:", robot.frontRight.getRPM());
        telemetry.addData("Back left RPM:", robot.backLeft.getRPM());
        telemetry.addData("Back right RPM:", robot.backRight.getRPM());


        if (gamepad1.right_bumper || gamepad2.y) {
            robot.intakeSystem.intake();
        } else if (gamepad1.left_bumper || gamepad2.a) {
            robot.intakeSystem.outtake();
        } else {
            robot.intakeSystem.stop();
        }

        if (gamepad1.right_trigger > 0) {
            robot.lift.raise();
        } else if (gamepad1.left_trigger > 0) {
            robot.lift.lower();
        } else {
            robot.lift.stop();
        }

        if (gamepad1.a) {
            robot.frontLeft.setPower(0);
        }

        if (gamepad2.right_bumper) {
            robot.lift.raiseRight();
        } else if (gamepad2.left_bumper) {
            robot.lift.raiseLeft();
        }

        if (gamepad2.right_trigger > 0) {
            robot.lift.lowerRight();
        } else if (gamepad2.left_trigger > 0) {
            robot.lift.lowerLeft();
        }

        if (gamepad2.b || gamepad1.right_bumper) {
            robot.conveyorSystem.up();
        } else if (gamepad2.x || gamepad1.right_bumper) {
            robot.conveyorSystem.down();
        } else {
            robot.conveyorSystem.stop();
        }

        if (gamepad2.dpad_up) {
            robot.conveyorSystem.rightUp();
        } else if (gamepad2.dpad_down){
            robot.conveyorSystem.rightDown();
        }

        if (gamepad2.dpad_right) {
            robot.conveyorSystem.leftUp();
        } else if (gamepad2.dpad_left){
            robot.conveyorSystem.leftDown();
        }

        //telemetry.addData("Robot angle:", robot.imu.getAngles()[0]);

        /*if (gamepad1.dpad_up) {
            robot.relicWrist.setPosition(.5);
        } else if (gamepad1.dpad_down) {
            robot.relicWrist.setPosition(0);
        }

        if (gamepad1.dpad_right) {
            robot.relicGrabber.setPosition(.8);
        } else if (gamepad1.dpad_left) {
            robot.relicGrabber.setPosition(.25);
        }*/
    }
}