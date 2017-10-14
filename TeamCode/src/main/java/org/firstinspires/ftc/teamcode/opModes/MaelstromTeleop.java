package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.Hardware;


@TeleOp(name= "Telop")
//@Disabled
public class MaelstromTeleop extends OpMode {

    Hardware robot = new Hardware();


    public void init() {

        robot.init(hardwareMap);

        telemetry.addLine("Omit the first noun");
        telemetry.update();

    }

    public void loop() {

    }
}