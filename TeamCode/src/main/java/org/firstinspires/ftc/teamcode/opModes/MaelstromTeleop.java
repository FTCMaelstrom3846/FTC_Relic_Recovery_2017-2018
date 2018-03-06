package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

//import org.firstinspires.ftc.teamcode.control.GamepadInputFilter;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

import java.io.FileWriter;

@TeleOp(name= "Telop")
//@Disabled
public class MaelstromTeleop extends OpMode {

    Hardware robot = new Hardware();

    //GamepadInputFilter gamepadFilter = new GamepadInputFilter();

    boolean dpadRightCurrState = false;
    boolean dpadRightPreviousState = false;
    boolean aCurrState = false;
    boolean aPreviousState = false;
    boolean gripperOpen = true;
    boolean panGripperOpen = true;
    double currTime = 0;
    double previousTime = 0;

    public void init() {

        robot.init(hardwareMap);

        telemetry.addLine("Omit the first noun");
        telemetry.update();
        previousTime = System.nanoTime()/1e6;
    }

    public void loop() {
        currTime = System.nanoTime()/1e6;
        double elapsedTime = currTime - previousTime;
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
        } /*else if (gamepad2.right_bumper) {
            robot.relicGrabberSystem.placeRelic();
        }*/

        telemetry.addData("Relic extender position", robot.relicExtender.getCurrentPosition());

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
           panGripperOpen = true;
       } else if (gamepad1.a || gamepad2.a) {
           robot.dumpPan.lowerPan();

           panGripperOpen = false;
       } else if (gamepad1.x || gamepad2.x) {
           if (elapsedTime > 8) {
               robot.dumpPan.centerPan();
               panGripperOpen = true;
           }

       }

        if (gamepad1.right_trigger > 0/* || gamepad2.y*/) {
            robot.intakeSystem.intake();
        } else if (gamepad1.left_trigger > 0/* || gamepad2.a*/) {
            robot.intakeSystem.outtake();
        } else {
            robot.intakeSystem.stop();
        }

        if (gamepad1.right_bumper || gamepad2.right_bumper) {
            robot.lift.lower();
        } else if (gamepad1.left_bumper || gamepad2.left_bumper) {
            robot.lift.raise();
        } else {
            robot.lift.stop();
        }

        if (gamepad1.b || gamepad2.b) { //MAKE TOGGLE CLASS
            aCurrState = true;
        } else {
            aCurrState = false;
            if (aPreviousState) {
                panGripperOpen = !panGripperOpen;
            }
        }

        aPreviousState = aCurrState;

        if (panGripperOpen) {
            robot.dumpPan.openGripper();
            telemetry.addLine("succ");
        } else {
            robot.dumpPan.closeGripper();
            telemetry.addLine("success");
        }

        /*if (gamepad1.b)
        {
            robot.dumpPan.closeGripper();
        }
        else
        {
            robot.dumpPan.openGripper();
        }*/

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


        //telemetry.addData("Robot angle:", robot.imu.getRelativeYaw()[0]);

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
        previousTime = elapsedTime > 8 ?  System.nanoTime()/1e6 : previousTime;
    }
}