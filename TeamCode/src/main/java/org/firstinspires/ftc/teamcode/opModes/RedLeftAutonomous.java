package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.control.Utils;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.sensors.VumarkRecognition;

@Autonomous(name="Red Left Autonomous ")
//@Disabled
public class RedLeftAutonomous extends LinearOpMode implements Utils.AutonomousOpMode, Runnable {

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

        column = vumark.getColumn();
        telemetry.addData("Detected vumark", column);
        telemetry.update();


        robot.jewelArmSystem.lower();
        sleep(1000);
        if (robot.jewelSensor.blue() > robot.jewelSensor.red()) {
            robot.jewelArmSystem.turnWristLeft();
        } else if (robot.jewelSensor.red() > robot.jewelSensor.blue()) {
            robot.jewelArmSystem.turnWristRight();
        }

        sleep(600);
        robot.jewelArmSystem.raise();

        Thread extendo = new Thread(this);
        extendo.start();

        //robot.drivetrain.drive(-1250, 0, 0.6);

        robot.drivetrain.drive(-1500, 0, 0.6);

        robot.drivetrain.driveForTime(0.35, 0, 0.75);

        switch (column) {

            case LEFT:
                robot.drivetrain.drive(-1400, 0, 0.6);
                break;
            case CENTER:
                robot.drivetrain.drive(-1075, 0, 0.6);
                break;
            case UNKNOWN:

            case RIGHT:
                robot.drivetrain.drive(-550, 0, 0.6);
                break;
        }

        robot.drivetrain.turnAngle(-90, 1);

        //robot.drivetrain.intakeFlipAndCryptoLineup(-575, 1, 0.4);

        robot.drivetrain.driveForTime(-0.25, 0, 3);

        robot.drivetrain.drive(400, 0, 1);

        robot.dumpPan.centerPan();

        robot.dumpPan.raisePanAuto(40);

        robot.drivetrain.drive(400, 0, 1);

        robot.dumpPan.lowerPan();

        robot.drivetrain.driveForTime(-0.45, 0, 0.65);

        robot.drivetrain.driveForTime(0.35, 0, 0.65);


/*
        robot.intakeSystem.intake();

        robot.drivetrain.drive(1900, 0, 1);

        robot.intakeSystem.outtake();

        robot.drivetrain.drive(-2100, 0, 1);

        robot.intakeSystem.stop();

        robot.lift.raise();

        sleep(750);

        robot.lift.stop();

        robot.dumpPan.centerPan();

        robot.dumpPan.raisePan();

        robot.drivetrain.driveForTime(-0.25, 0, 0.65);

        robot.drivetrain.drive(400, 0, 1);

        robot.dumpPan.lowerPan();*/


        //robot.drivetrain.drive(-575, 0, 0.6);

        //robot.drivetrain.driveTillUltrasonicDistance(5, 0, 1);

        //robot.drivetrain.strafeTillColumn(/*RelicRecoveryVuMark.RIGHT, */Utils.AutoColor.RED, .75, -90);

        //robot.drivetrain.drive(200, 0, 1);

/*        robot.dumpPan.centerPan();

        robot.dumpPan.raisePanAuto();*/

/*        sleep(1000);

        robot.drivetrain.drive(200, 0, 1);

        robot.dumpPan.lowerPan();

        sleep(500);

        robot.intakeSystem.intake();

        robot.drivetrain.drive(1000, 0, 1);

        robot.intakeSystem.stop();

        robot.drivetrain.drive(-1000, 0, 1);

        *//*        robot.dumpPan.centerPan();

        robot.dumpPan.raisePanAuto();*//*

        sleep(1000);

        robot.dumpPan.lowerPan();*/
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
