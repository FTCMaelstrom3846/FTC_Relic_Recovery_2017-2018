package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.control.Utils;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.sensors.VumarkRecognition;

@Autonomous(name="Blue Right Autonomous ")
//@Disabled
public class BlueRightAutonomous extends LinearOpMode implements Utils.AutonomousOpMode {

    Hardware robot = new Hardware();

    RelicRecoveryVuMark column = RelicRecoveryVuMark.RIGHT;

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
        column = vumark.getColumn();
        telemetry.addData("Detected vumark", column);
        telemetry.update();

        robot.jewelArms.lowerLeft();
        sleep(1000);
*/
/*        telemetry.addData("Blue", robot.jewelSensor.blue());
        telemetry.addData("Red", robot.jewelSensor.red());*//*

        if (robot.jewelSensor.blue() < robot.jewelSensor.red()) {
            //robot.drivetrain.drive(300, 0, 0.68);
            robot.jewelArms.turnWristRight();
            sleep(600);
            robot.jewelArms.raiseLeft();
            //robot.drivetrain.drive(1750, 0, 0.6);

        } else if (robot.jewelSensor.red() < robot.jewelSensor.blue()) {
            //robot.drivetrain.drive(-300, 0, 0.68);
            robot.jewelArms.turnWristLeft();
            sleep(600);
            robot.jewelArms.raiseLeft();
            //robot.drivetrain.drive(2400, 0, 0.6);

        }
*/

/*        robot.drivetrain.drive(700, 0, 0.6);

        sleep(1000);

        //column = vumark.getColumn();
        telemetry.addData("Detected vumark", column);
        telemetry.update();*/
        //sleep(2000);

        //robot.drivetrain.drive(1525, 0, 0.6);

        //robot.drivetrain.turnAngle(-90, 1);

/*
        robot.drivetrain.driveForTime(0.55, -90, 1.5);
*/

        //robot.drivetrain.intakeFlipAndCryptoLineup(-575, 1, 0.4);

/*        robot.drivetrain.driveForTime(0.55, -90, 1.5);

        robot.drivetrain.driveForTime(-0.5, 0, 2);

        //robot.drivetrain.turnAngle(180, 1);

        robot.drivetrain.drive(100, 0, 1);*/

/*
        robot.drivetrain.driveForTime(1, 0, 0.4);

        robot.drivetrain.driveForTime(-0.35, 0, 3);

        robot.drivetrain.drive(100, 0, 1);
*/


        /*robot.drivetrain.drive(1450, 0, 0.6);*/

        robot.drivetrain.drive(1500, 0, 0.6);

        robot.drivetrain.driveForTime(-0.35, 0, 0.75);

        switch (column) {
            case UNKNOWN:

            case LEFT:
                robot.drivetrain.drive(650, 0, 0.6);
                break;

            case CENTER:
                robot.drivetrain.drive(1175, 0, 0.6);
                break;
            case RIGHT:
                robot.drivetrain.drive(1580, 0, 0.6);
                break;
        }


        robot.intakeSystem.extendDropper();

        robot.drivetrain.turnAngle(-90, 1);

        robot.intakeSystem.retractDropper();

        //robot.drivetrain.intakeFlipAndCryptoLineup(-560, 1, 0.4);
        //robot.drivetrain.drive(-575, 0, 0.6);

        robot.drivetrain.driveForTime(-0.25, 0, 3);


        //robot.drivetrain.strafeTillColumn(/*RelicRecoveryVuMark.LEFT, */Utils.AutoColor.BLUE, .75, 90);

        //robot.drivetrain.drive(200, 0, 1);

/*        robot.dumpPan.centerPan();

        robot.dumpPan.raisePanAuto();*/

        /*sleep(1000);

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


    public boolean getOpModeIsActive() {
        return opModeIsActive();
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }


}
