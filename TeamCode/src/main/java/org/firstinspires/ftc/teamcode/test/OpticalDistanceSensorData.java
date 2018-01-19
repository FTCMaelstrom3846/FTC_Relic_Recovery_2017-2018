/*
package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@TeleOp(name="Optical Distance Sensor Test")

public class OpticalDistanceSensorData extends LinearOpMode {


    Hardware robot = new Hardware();
    @Override
    public void runOpMode() {
        FileWriter writer;
        try {
            writer = new FileWriter(new File("OptoData.csv"));
            //writer.append("Elapsed Time,Right Opto Distance,Left Opto Distance\n");
        }
        catch (IOException e) {}

        robot.init(hardwareMap);

        telemetry.addLine("Omit the first noun");
        telemetry.update();

        waitForStart();

        ElapsedTime elapsedTime = new ElapsedTime();

        while (opModeIsActive()) {
            telemetry.addData("Elapsed time", elapsedTime.seconds());
            try {
                writer.append(String.valueOf(robot.rightLiftDistance.getRawLightDetected()));
                writer.append(String.valueOf(robot.leftLiftDistance.getRawLightDetected()));
            }
            catch (IOException e) {}
        }
        try {
            writer.close();
        }
        catch (IOException e) {}
    }
}*/
