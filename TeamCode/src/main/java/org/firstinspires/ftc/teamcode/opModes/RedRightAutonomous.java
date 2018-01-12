package org.firstinspires.ftc.teamcode.opModes;

import android.hardware.Camera;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.control.AutonomousOpMode;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.sensors.VumarkRecognition;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@Autonomous(name="Red Right Autonomous ")
//@Disabled
public class RedRightAutonomous extends LinearOpMode implements AutonomousOpMode {

    Hardware robot = new Hardware();


    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        robot.drivetrain.setAuto(this);

        VumarkRecognition vumark = new VumarkRecognition(/*hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName())*/);
        vumark.initVumark();

        telemetry.addLine("Omit the first noun");
        telemetry.update();


        waitForStart();
/*
        robot.rightJewelArm.setPosition(0.35);
        sleep(1000);
        robot.jewelArms.lowerRight();
        sleep(2500);
        telemetry.addData("Blue", robot.jewelSensor.blue());
        telemetry.addData("Red", robot.jewelSensor.red());
        if (robot.jewelSensor.blue() > 0) {
            robot.drivetrain.drive(300, 0, 1);
            robot.jewelArms.raiseRight();
            sleep(1000);
            robot.drivetrain.drive(1750, 0, 0.6);

        } else if (robot.jewelSensor.red() > 0) {
            robot.drivetrain.drive(-300, 0, 1);
            robot.jewelArms.raiseRight();
            sleep(1000);
            robot.drivetrain.drive(2150, 0, 0.6);

        }*/

        //robot.drivetrain.drive(1750, 0, 0.6);

        robot.drivetrain.turnAngle(180, 1);

        //robot.drivetrain.drive(1750, -90, 0.6);

        //robot.drivetrain.strafeTillColumn(Drivetrain.Column.Right, 0.55);

        //robot.drivetrain.drive(200, 0, 1);

        //robot.dumpPan.raisePan();

        //sleep(1750);

        //robot.dumpPan.lowerPan();

        //sleep(500);
    }


    public boolean getOpModeIsActive() {
        return opModeIsActive();
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }

}