package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.sensors.BNO055_IMU;
import org.firstinspires.ftc.teamcode.sensors.VumarkRecognition;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.PID;


@Autonomous(name="Test", group ="Test")
//@Disabled

public class VumarkFollowingTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        Hardware robot = new Hardware();
        robot.init(hardwareMap);

        BNO055_IMU imu = new BNO055_IMU("IMU", hardwareMap);

        VumarkRecognition vumark = new VumarkRecognition();
        vumark.initVumark();

        waitForStart();

        PID distancePID = new PID(.0005, 0, 0, 0, 0);
        PID anglePID = new PID(.0075, 0, 0, 0, 0);
        long startTime = System.nanoTime();
        while (opModeIsActive()) {
            double deltaTime = (System.nanoTime() - startTime)/1e6;
            if (deltaTime > 1) {
                double power = distancePID.power(Math.hypot(vumark.getXTranslation(), vumark.getZTranslation()), 0, .5, deltaTime);
                power += anglePID.power(vumark.getYRotation(), 0, .25, deltaTime);
                robot.leftMotor.setPower(power);
                robot.rightMotor.setPower(-power);
                startTime = System.nanoTime();
            }
        }
    }
}