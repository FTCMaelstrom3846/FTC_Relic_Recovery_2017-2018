package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.control.GamepadInputFilter;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;


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
        telemetry.addData("angle", robot.drivetrain.getTeleopAngle());

        telemetry.addData("Front left RPM:", robot.frontLeft.getRPM());
        telemetry.addData("Front right RPM:", robot.frontRight.getRPM());
        telemetry.addData("Back left RPM:", robot.backLeft.getRPM());
        telemetry.addData("Back right RPM:", robot.backRight.getRPM());


        if (gamepad1.right_bumper) {
            /*robot.conveyorBottomLeft.setPower(0.75);
            robot.conveyorBottomRight.setPower(0.75);
            robot.conveyorTopLeft.setPower(0.75);*/
            robot.conveyor.run(0.75);
        } else if (gamepad1.left_bumper) {
            robot.conveyor.run(-0.75);
            /*robot.conveyorBottomLeft.setPower(-0.75);
            robot.conveyorBottomRight.setPower(-0.75);
            robot.conveyorTopLeft.setPower(-0.75);*/
        } else {
            robot.conveyor.run(0);
            /*robot.conveyorBottomLeft.setPower(0);
            robot.conveyorBottomRight.setPower(0);
            robot.conveyorTopLeft.setPower(0);
*/        }

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