package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

/**
 * Created by Ankit V on 3/4/2018.
 */

public class MaxbotixUltrasonicSensor implements DistanceSensor {


    private final AnalogInput rangeSensor;

    public MaxbotixUltrasonicSensor(AnalogInput rangeSensor) {
        this.rangeSensor = rangeSensor;
    }

/*    @Override
    public String toString() {
        return String.format("OpticalDistanceSensor: %1.2f", getLightDetected());
    }*/


    public double getMaxVoltage() {
        // The sensor itself is a 5v sensor, reporting analog values from 0v to 5v. However, depending
        // on the level conversion hardware that might be between us and the sensor, that may get shifted
        // to a different range. We'll assume that we only ever shift *down* in range, not up, so we
        // can take the min of the sensor's natural level and what the input controller can do.
        final double sensorMaxVoltage = 3.3;
        return Math.min(sensorMaxVoltage, rangeSensor.getMaxVoltage());
    }

    public double mmDistance() {
        double reading =  Range.clip(readRawVoltage(), 0, getMaxVoltage());
        return (reading/(getMaxVoltage()/1023))*5;
    }


    public double readRawVoltage() {
        return rangeSensor.getVoltage();
    }

    @Override
    public double getDistance(DistanceUnit unit) {

        double mm = mmDistance();
        return unit.fromUnit(DistanceUnit.MM, mm);
    }


    @Override public Manufacturer getManufacturer() {
        return Manufacturer.Adafruit;
    }

    @Override
    public String getDeviceName() {
        return AppUtil.getDefContext().getString(com.qualcomm.robotcore.R.string.configTypeOpticalDistanceSensor);
    }

    @Override
    public String getConnectionInfo() {
        return rangeSensor.getConnectionInfo();
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {
    }

    @Override
    public void close() {

    }
}
