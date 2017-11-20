package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

import ftc.vision.BeaconColorResult;
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

        robot.drivetrain.drive(1500, 0);

        robot.drivetrain.turnAngle(90);

        //robot.drivetrain.turnAngle(90);

        /*while (opModeIsActive()) {
            telemetry.addData("Right Front Encoder", robot.frontRight.getCurrentPosition());
            telemetry.addData("Left Front Encoder", robot.frontLeft.getCurrentPosition());
            telemetry.addData("Right Back Encoder", robot.backRight.getCurrentPosition());
            telemetry.addData("Left Back Encoder", robot.backLeft.getCurrentPosition());
            telemetry.update();
        }*/

        /*FrameGrabber frameGrabber = FtcRobotControllerActivity.frameGrabber; //Get the frameGrabber

        frameGrabber.grabSingleFrame(); //Tell it to grab a frame
        while (!frameGrabber.isResultReady()) { //Wait for the result
            sleep(5); //sleep for 5 milliseconds
        }

        //Get the result
        ImageProcessorResult imageProcessorResult = frameGrabber.getResult();
        BeaconColorResult result = (BeaconColorResult) imageProcessorResult.getResult();

        BeaconColorResult.BeaconColor leftColor = result.getLeftColor();
        BeaconColorResult.BeaconColor rightColor = result.getRightColor();

        telemetry.addData("Left color", leftColor);
        telemetry.addData("Right color", rightColor);

        if (leftColor == BeaconColorResult.BeaconColor.BLUE) {

        } else if (leftColor == BeaconColorResult.BeaconColor.RED) {

        }*/

    }

    public boolean getOpModeIsActive() {
        return opModeIsActive();
    }

}