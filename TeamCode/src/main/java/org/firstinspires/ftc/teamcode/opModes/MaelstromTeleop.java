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

    boolean dpadRightCurrState = false;
    boolean dpadRightPreviousState = false;
    boolean gripperOpen = true;

    public void init() {

        robot.init(hardwareMap);

        robot.relicGrabberSystem.resetWrist();

        robot.relicGrabberSystem.openGrabber();

        robot.jewelArms.resetArms();

        robot.dumpPan.lowerPan();

        telemetry.addLine("Omit the first noun");
        telemetry.update();
    }

    public void loop() {
        robot.drivetrain.drive(/*gamepadFilter.lazyLeftStickY*/(gamepad1.left_stick_y),
                /*gamepadFilter.lazyLeftStickX*/(gamepad1.left_stick_x), /*gamepadFilter.lazyRighStickX*/(gamepad1.right_stick_x));
/*        telemetry.addData("angle", Math.toDegrees(robot.drivetrain.getDriveAngle()));

        telemetry.addData("Front Left Power", robot.drivetrain.getFrontLeftPower());
        telemetry.addData("Back Left Power", robot.drivetrain.getBackLeftPower());
        telemetry.addData("Front Right Power", robot.drivetrain.getFrontRightPower());
        telemetry.addData("Back Right Power", robot.drivetrain.getBackRightPower());*/

        telemetry.addData("Front left RPM:", robot.frontLeft.getRPM());
        telemetry.addData("Front right RPM:", robot.frontRight.getRPM());
        telemetry.addData("Back left RPM:", robot.backLeft.getRPM());
        telemetry.addData("Back right RPM:", robot.backRight.getRPM());

/*
        telemetry.addData("Front right position:", robot.frontRight.getCurrentPosition());
*/

        if (gamepad2.right_trigger > 0) {
            robot.relicGrabberSystem.setPower(gamepad2.right_trigger);
        } else if (gamepad2.left_trigger > 0) {
            robot.relicGrabberSystem.setPower(-gamepad2.left_trigger);
        } else {
            robot.relicGrabberSystem.stop();
        }

        if (gamepad1.dpad_right || gamepad2.dpad_right) {
            robot.relicGrabberSystem.resetWrist();
        } else if (gamepad1.dpad_up || gamepad2.dpad_up) {
            robot.relicGrabberSystem.raiseWrist();
        } else if (gamepad1.dpad_left || gamepad2.dpad_left) {
            robot.relicGrabberSystem.lowerWrist();
        }

        if (gamepad1.dpad_down || gamepad2.dpad_down) { //MAKE TOGGLE CLASS
            dpadRightCurrState = true;
        } else {
            dpadRightCurrState = false;
            if (dpadRightPreviousState) {
                gripperOpen = !gripperOpen;
            }
        }

        dpadRightPreviousState = dpadRightCurrState;

        if (gripperOpen) {
            robot.relicGrabberSystem.openGrabber();
        } else {
            robot.relicGrabberSystem.closeGrabber();
        }

       if (gamepad1.y || gamepad2.y) {
           robot.dumpPan.raisePan();
       } else if (gamepad1.a || gamepad2.a) {
           robot.dumpPan.lowerPan();
       } else if (gamepad1.x || gamepad2.x) {
           telemetry.addLine("center");
           robot.dumpPan.centerPan();
       }

        if (gamepad1.right_trigger > 0/* || gamepad2.y*/) {
            robot.intakeSystem.intake();
        } else if (gamepad1.left_trigger > 0/* || gamepad2.a*/) {
            robot.intakeSystem.outtake();
        } else {
            robot.intakeSystem.stop();
        }

        if (gamepad1.right_bumper || gamepad2.right_bumper) {
            robot.lift.raise();
        } else if (gamepad1.left_bumper ||  gamepad2.left_bumper) {
            robot.lift.lower();
        } else {
            robot.lift.stop();
        }


/*        if (gamepad2.right_bumper) {
            robot.lift.raiseRight();
        } else if (gamepad2.left_bumper) {
            robot.lift.raiseLeft();
        }

        if (gamepad2.right_trigger > 0) {
            robot.lift.lowerRight();
        } else if (gamepad2.left_trigger > 0) {
            robot.lift.lowerLeft();
        }*/


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