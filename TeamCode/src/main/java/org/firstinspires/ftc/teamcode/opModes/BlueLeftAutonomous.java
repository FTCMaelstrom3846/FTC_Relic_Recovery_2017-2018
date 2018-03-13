package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.control.Utils;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.sensors.VumarkRecognition;

@Autonomous(name="Blue Left Autonomous ")
//@Disabled
public class BlueLeftAutonomous extends LinearOpMode implements Utils.AutonomousOpMode, Runnable {

    Hardware robot = new Hardware();

    RelicRecoveryVuMark column;

    @Override
    public void runOpMode() {

        robot.setAuto(this);

        robot.init(hardwareMap);


        VumarkRecognition vumark = new VumarkRecognition(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
        vumark.initVumark();

        telemetry.addLine("Omit the first noun");
        telemetry.update();


        waitForStart();
/*
        robot.drivetrain.turnAngle(30, 1);
        sleep(1000);*/

        column = vumark.getColumn();
        telemetry.addData("Detected vumark", column);
        telemetry.update();
/*
        sleep(4000);

        robot.drivetrain.turnAngle(-30, 1);
        */
        robot.jewelArmSystem.lower();
        sleep(1000);
        if (robot.jewelSensor.blue() < robot.jewelSensor.red()) {
            robot.jewelArmSystem.turnWristLeft();
        } else if (robot.jewelSensor.red() < robot.jewelSensor.blue()) {
            robot.jewelArmSystem.turnWristRight();
        }

        sleep(600);
        robot.jewelArmSystem.raise();

        robot.drivetrain.drive(1500, 0, 0.6);

        robot.drivetrain.driveForTime(-0.35, 0, 0.75);

        robot.drivetrain.drive(400, 0, 0.6);

        Thread extendo = new Thread(this);
        extendo.start();

        switch (column) {
            case UNKNOWN:

            case LEFT:
                robot.drivetrain.turnAngle(163, 1, 0.2);
                robot.drivetrain.drive(-300, 0, 0.6);
                break;

            case CENTER:
                robot.drivetrain.turnAngle(147 , 1, 0.2);
                robot.drivetrain.drive(-300, 0, 0.6);
                break;

            case RIGHT:
                robot.drivetrain.turnAngle(130, 1, 0.2);
                robot.drivetrain.drive(-550, 0, 0.6);
                break;
        }

        robot.dumpPan.centerPan();

        sleep(300);

        robot.dumpPan.raisePanAuto(40);

        sleep(750);

        robot.drivetrain.driveForTime(-0.4, 0, 1);

        robot.drivetrain.drive(200, 0, 1);

        robot.dumpPan.lowerPan();
/*
*//*        robot.drivetrain.drive(700, 0, 0.6);

        sleep(1000);

        //column = vumark.getColumn();
        telemetry.addData("Detected vumark", column);
        telemetry.update();*//*
        //sleep(2000);

        robot.drivetrain.drive(1200, 0, 0.6);

        robot.drivetrain.turnAngle(180, 1);

        robot.drivetrain.driveForTime(0.25, 0, 1);

        //robot.drivetrain.turnAngle(180, 1);

        robot.drivetrain.drive(-950, 0, 1);

        robot.drivetrain.strafeTillColumn(RelicRecoveryVuMark.LEFT, Utils.AutoColor.RED, .55, 90);

        robot.drivetrain.drive(200, 0, 1);

        robot.dumpPan.centerPan();

        robot.dumpPan.raisePanAuto();

        sleep(750);

        robot.drivetrain.drive(200, 0, 1);

        robot.dumpPan.lowerPan();

        sleep(500);*/
    }


    public void run() {
        robot.intakeSystem.extendDropper();

        sleep(6750);

        robot.intakeSystem.stopDropper();
    }

    public boolean getOpModeIsActive() {
        return opModeIsActive();
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }


}
