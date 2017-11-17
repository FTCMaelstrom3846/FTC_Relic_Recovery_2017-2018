package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//import org.firstinspires.ftc.teamcode.control.GamepadInputFilter;
import org.firstinspires.ftc.teamcode.hardware.Hardware;


@TeleOp(name= "Telop")
//@Disabled
public class MaelstromTeleop extends OpMode {

    Hardware robot = new Hardware();
    Boolean conveyorRightIndividualRunning = false;
    Boolean conveyorLeftIndividualRunning = false;
    boolean conveyorWholeRunning = false;
    //GamepadInputFilter gamepadFilter = new GamepadInputFilter();


    public void init() {

        robot.init(hardwareMap);

        robot.relicGrabberSystem.lowerWrist();

        robot.relicGrabberSystem.closeGrabber();

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

        telemetry.addData("Front right position:", robot.frontRight.getCurrentPosition());

        if (gamepad1.dpad_left) {
            robot.relicGrabberSystem.extend();
        } else if (gamepad1.dpad_right) {
            robot.relicGrabberSystem.retract();
        } else {
            robot.relicGrabberSystem.stop();
        }

        if (gamepad1.dpad_up) {
            robot.relicGrabberSystem.raiseWrist();
        } else if (gamepad1.dpad_down) {
            robot.relicGrabberSystem.lowerWrist();
        }

        if (gamepad1.y) {
            robot.relicGrabberSystem.openGrabber();
        } else if (gamepad1.a) {
            robot.relicGrabberSystem.closeGrabber();
        }

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
            conveyorWholeRunning = true;
        } else if (gamepad2.x || gamepad1.right_bumper) {
            robot.conveyorSystem.down();
            conveyorWholeRunning = true;
        } else {
            conveyorWholeRunning = false;
        }

        if (gamepad2.dpad_up) {
            robot.conveyorSystem.rightUp();
            conveyorRightIndividualRunning = true;
        } else if (gamepad2.dpad_down){
            robot.conveyorSystem.rightDown();
            conveyorRightIndividualRunning = true;
        } else {
            conveyorRightIndividualRunning = false;
        }

        if (gamepad2.dpad_right) {
            robot.conveyorSystem.leftUp();
            conveyorLeftIndividualRunning = true;
        } else if (gamepad2.dpad_left){
            robot.conveyorSystem.leftDown();
            conveyorLeftIndividualRunning = true;
        } else {
            conveyorLeftIndividualRunning = false;
        }

        if (!conveyorRightIndividualRunning && !conveyorWholeRunning && !conveyorLeftIndividualRunning) {
            robot.conveyorSystem.stop();
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