/*
package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.sensors.VumarkRecognition;
import org.firstinspires.ftc.teamcode.hardware.MinibotHardware;
import org.firstinspires.ftc.teamcode.control.PIDController;


@Autonomous(name="Vumark Following", group ="Test")
@Disabled

public class VumarkFollowingTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        MinibotHardware robot = new MinibotHardware();
        robot.init(hardwareMap);

        VumarkRecognition vumark = new VumarkRecognition();
        vumark.initVumark();

        waitForStart();

        PIDController distancePID = new PIDController(.0005, 0, 0, 0);
        PIDController anglePID = new PIDController(.0075, 0, 0, 0);
        long startTime = System.nanoTime();
        while (opModeIsActive()) {
            double deltaTime = (System.nanoTime() - startTime)/1e6;
            if (deltaTime > 1) {
                double power = distancePID.power(Math.hypot(vumark.getXTranslation(), vumark.getZTranslation()), 0, .5, deltaTime);
                power += anglePID.power(vumark.getYRotation(), 0, .25, deltaTime);
                robot.leftMotor.setPower(power);
                robot.rightMotor.setPower(-power);
                telemetry.addData("Power", power);
                startTime = System.nanoTime();
            }
        }
    }
}*/
