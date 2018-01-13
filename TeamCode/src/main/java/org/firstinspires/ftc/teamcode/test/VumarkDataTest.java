package org.firstinspires.ftc.teamcode.test;

import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.sensors.VumarkRecognition;

@Autonomous(name="Vumark Data", group ="Test")
//@Disabled

public class VumarkDataTest extends LinearOpMode {

    @Override
    public void runOpMode() {

        //Uncomment if you want the camera to appear on the phone
        VumarkRecognition vumark = new VumarkRecognition(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
        vumark.initVumark();


        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("X", vumark.getXTranslation());
            telemetry.addData("Y", vumark.getYTranslation());
            telemetry.addData("Z", vumark.getZTranslation());

            telemetry.addData("X rotation", vumark.getXRotation());
            telemetry.addData("Y rotation", vumark.getYRotation());
            telemetry.addData("Z rotation", vumark.getZRotation());

            telemetry.addData("Vumark", vumark.getColumnString());

            telemetry.update();
        }
    }
}