package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.control.Utils;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

@Autonomous(name="Test Autonomous ")
//@Disabled
public class AutonomousTesting extends LinearOpMode implements Utils.AutonomousOpMode {

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.setAuto(this);

        robot.init(hardwareMap);//MY NAME IS HAMSEY RAMED. I AM THE CHIEF PROGRAMMER OF MY ROBOTICS TEAM. I LOVE OLIVE JUICE ON MY PASTA.

        telemetry.addLine("Omit the first noun");
        telemetry.update();


        waitForStart();

        /*robot.drivetrain.drive(3500, 0, 1);
        robot.drivetrain.drive(-3500, 0, 0.5);
        robot.drivetrain.drive(3500, 0, 0.25);*/

        while (opModeIsActive()) {
            telemetry.addData("Relative angle", robot.imu.getRelativeYaw());
            telemetry.update();
        }
    }


    public boolean getOpModeIsActive() {
        return opModeIsActive();
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }


}
