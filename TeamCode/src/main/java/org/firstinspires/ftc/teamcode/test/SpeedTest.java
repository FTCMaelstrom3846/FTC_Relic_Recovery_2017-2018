package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.PIDController;
import org.firstinspires.ftc.teamcode.hardware.Hardware;


@TeleOp(name= "SpeedTest")
@Disabled
public class SpeedTest extends OpMode {

    Hardware robot = new Hardware();

    //GamepadInputFilter gamepadFilter = new GamepadInputFilter();

    PIDController rightLiftPID = new PIDController(0.0000000000000045, 0, 0, 0);
    PIDController leftLiftPID = new PIDController(0.0000000000000045, 0, 0, 0);

    public void init() {

        robot.init(hardwareMap);

        telemetry.addLine("Omit the first noun");
        telemetry.update();
    }

    public void loop() {
        telemetry.addData("Right Lift RPM:", robot.rightLift.getRPM());
        telemetry.addData("Left Lift RPM:", robot.leftLift.getRPM());
        telemetry.addData("Left Position", robot.leftLift.getCurrentPosition());

        robot.rightLift.setRPM(20/*rightLiftPID.power(400, robot.rightLift.getCurrentPosition())*/);
        robot.leftLift.setRPM(-20/*leftLiftPID.power(-400, robot.leftLift.getCurrentPosition())*/);
        //robot.leftLift.setRPM(-20);

    }
}