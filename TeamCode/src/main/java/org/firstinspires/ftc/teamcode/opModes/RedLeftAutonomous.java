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
public class RedLeftAutonomous extends LinearOpMode implements Utils.AutonomousOpMode {

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

/*        column = vumark.getColumn();
        telemetry.addData("Detected vumark", column);
        telemetry.update();


        robot.jewelArms.lowerLeft();
        sleep(1000);
*//*        telemetry.addData("Blue", robot.jewelSensor.blue());
        telemetry.addData("Red", robot.jewelSensor.red());*//*
        if (robot.jewelSensor.blue() > robot.jewelSensor.red()) {
            robot.jewelArms.turnWristRight();
            sleep(600);
            robot.jewelArms.raiseLeft();

        } else if (robot.jewelSensor.red() > robot.jewelSensor.blue()) {
            robot.jewelArms.turnWristLeft();
            sleep(600);
            robot.jewelArms.raiseLeft();

        }*/

/*        robot.drivetrain.drive(-1450, 0, 0.6);

        robot.drivetrain.turnAngle(-90, 1);

        //robot.drivetrain.intakeFlipAndCryptoLineup(-575, 1, 0.4);*/

        robot.drivetrain.driveTillUltrasonicDistance(5, 0, 1);

/*
        robot.drivetrain.strafeTillColumn(RelicRecoveryVuMark.RIGHT, Utils.AutoColor.RED, .75, -90);

        robot.drivetrain.drive(200, 0, 1);

        robot.dumpPan.centerPan();

        robot.dumpPan.raisePanAuto();

        sleep(1000);

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
