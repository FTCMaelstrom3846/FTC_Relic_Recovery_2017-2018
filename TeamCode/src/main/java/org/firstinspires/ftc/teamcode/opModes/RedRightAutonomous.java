package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.control.Utils;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

@Autonomous(name="Red Right Autonomous ")
//@Disabled
public class RedRightAutonomous extends LinearOpMode implements Utils.AutonomousOpMode {

    Hardware robot = new Hardware();

    RelicRecoveryVuMark column;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

/*        VumarkRecognition vumark = new VumarkRecognition(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
        vumark.initVumark();*/

        telemetry.addLine("Omit the first noun");
        telemetry.update();


        waitForStart();

/*        robot.drivetrain.turnAngle(30, 1);
        sleep(1000);

        column = vumark.getColumn();
        telemetry.addData("Detected vumark", column);
        telemetry.update();

        sleep(4000);

        robot.drivetrain.turnAngle(-30, 1);*/
        robot.jewelArmSystem.lower();
        sleep(1000);
        if (robot.jewelSensor.blue() > robot.jewelSensor.red()) {
            robot.jewelArmSystem.turnWristLeft();
        } else if (robot.jewelSensor.red() > robot.jewelSensor.blue()) {
            robot.jewelArmSystem.turnWristRight();
        }

        sleep(600);
        robot.jewelArmSystem.raise();

/*        robot.drivetrain.drive(700, 0, 0.6);

        sleep(1000);

        //column = vumark.getColumn();
        telemetry.addData("Detected vumark", column);
        telemetry.update();*//*
        //sleep(2000);

        robot.drivetrain.drive(-1200, 0, 0.6);

        robot.drivetrain.driveForTime(0.25, 0, 1);

        //robot.drivetrain.turnAngle(180, 1);

        robot.drivetrain.drive(-950, 0, 1);

        robot.drivetrain.strafeTillColumn(RelicRecoveryVuMark.LEFT, Utils.AutoColor.RED, .55, -90);

        robot.drivetrain.drive(200, 0, 1);

        robot.dumpPan.centerPan();

        robot.dumpPan.raisePanAuto();

        sleep(750);

        robot.drivetrain.drive(200, 0, 1);

        robot.dumpPan.lowerPan();

        sleep(500);*/
    }


    public boolean getOpModeIsActive() {
        return opModeIsActive();
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }


}
