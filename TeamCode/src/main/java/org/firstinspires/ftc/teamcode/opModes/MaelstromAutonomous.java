package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;

import ftc.vision.JewelColorResult;
import ftc.vision.FrameGrabber;
import ftc.vision.ImageProcessorResult;

@Autonomous(name="Autonomous")
//@Disabled
public class MaelstromAutonomous extends LinearOpMode {

    Hardware robot = new Hardware();



    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        robot.drivetrain.setAuto(this);

        telemetry.addLine("Omit the first noun");
        telemetry.update();

        waitForStart();

/*        robot.drivetrain.drive(1640, 0, 0.5);

        //robot.drivetrain.turnAngle(-90);

        robot.drivetrain.drive(-500, 0, 0.5);

        telemetry.addLine("Outtake");*/


        //robot.drivetrain.turnAngle(90);

        /*while (opModeIsActive()) {
            telemetry.addData("Right Front Encoder", robot.frontRight.getCurrentPosition());
            telemetry.addData("Left Front Encoder", robot.frontLeft.getCurrentPosition());
            telemetry.addData("Right Back Encoder", robot.backRight.getCurrentPosition());
            telemetry.addData("Left Back Encoder", robot.backLeft.getCurrentPosition());
            telemetry.update();
        }*/
        /*CameraBridgeViewBase javaCameraView = FtcRobotControllerActivity;


        while (opModeIsActive()) {

            javaCameraView.turnOnFlash();
            sleep(300);
            javaCameraView.turnOffFlash();
            sleep(300);
        }*/
        FrameGrabber frameGrabber = FtcRobotControllerActivity.frameGrabber; //Get the frameGrabber

        frameGrabber.grabSingleFrame(); //Tell it to grab a frame
        while (!frameGrabber.isResultReady()) { //Wait for the result
            sleep(5); //sleep for 5 milliseconds
        }

        //Get the result
        ImageProcessorResult imageProcessorResult = frameGrabber.getResult();
        JewelColorResult result = (JewelColorResult) imageProcessorResult.getResult();

        JewelColorResult.JewelColor leftColor = result.getLeftColor();
        JewelColorResult.JewelColor rightColor = result.getRightColor();

        telemetry.addData("Left color", leftColor);
        telemetry.addData("Right color", rightColor);

        if (leftColor == JewelColorResult.JewelColor.BLUE) {
            telemetry.addLine("Left jewel is blue");
        } else if (leftColor == JewelColorResult.JewelColor.RED) {
            telemetry.addLine("Left jewel is red");
        }

        sleep(30000);

    }

    public boolean getOpModeIsActive() {
        return opModeIsActive();
    }

}