package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.control.AutonomousOpMode;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

@Autonomous(name="Test Autonomous ")
@Disabled
public class AutonomousTesting extends LinearOpMode implements AutonomousOpMode {

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        robot.drivetrain.setAuto(this);

        telemetry.addLine("Omit the first noun");
        telemetry.update();


        waitForStart();

        robot.drivetrain.drive(3500, 0, 1);
        robot.drivetrain.drive(-3500, 0, 0.5);
        robot.drivetrain.drive(3500, 0, 0.25);
    }


    public boolean getOpModeIsActive() {
        return opModeIsActive();
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }


}
