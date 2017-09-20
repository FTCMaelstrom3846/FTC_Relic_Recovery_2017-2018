package org.firstinspires.ftc.teamcode.sensors;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

public class VumarkRecognition {

    //Uncomment if you want the camera to appear on the phone
/*
    public VumarkRecognition(int cameraMonitorViewId) {
        this.cameraMonitorViewId = cameraMonitorViewId;
    }

    int cameraMonitorViewId;
*/
    private VuforiaLocalizer vuforia;

    private VuforiaTrackable relicTemplate;

    private RelicRecoveryVuMark vuMark;

    private double tX;
    private double tY;
    private double tZ;

    private double rX;
    private double rY;
    private double rZ;

    public void initVumark() {

        //Uncomment if you want the camera to appear on the phone
        //VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        //Comment out the below line if you want the camera to appear on the phone
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = "AVl6gwP/////AAAAGa8jT/RfpE26ushJ0mWKbf4CupRXGv5ChA0n1XlqbXmcEYXNp4wlDA4CDpTqEifWDpOf5uXTtcj4u/stgQP/2SC6LVRmejm/xIkPmB/6qvQbs12GsEJ3u6560xCSdeZdZKw8BG178BYnzH3pYtsoZk5He4j73287s19mFq2WUsfzj+DTfu3tJuUH3NnCJ2uNgRqXzrYERs82A/RMLcYh3tHsUjqTOU0y9MGO8WNWwQiq4DMj9L0pr59ltzPp3qwIHN5xU3xK5sxng0Y78vGvCPQlwsrUizD6Wek4NNE1LP/qEj8nAOnD+nYgYLNCUGtqHOWnGEAeuTA5iQ5UHKPz6cDsu7HjagFCmN5XbSzjyHg9\n";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);

        relicTrackables.activate();

    }

    void updatePos() {

        vuMark = RelicRecoveryVuMark.from(relicTemplate);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();

            if (pose != null) {
                VectorF trans = pose.getTranslation();
                Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                tX = trans.get(0);
                tY = trans.get(1);
                tZ = trans.get(2);

                rX = rot.firstAngle;
                rY = rot.secondAngle;
                rZ = rot.thirdAngle;
            }
        }


    }

    public double getXTranslation() {updatePos(); return tX;}
    public double getYTranslation() {updatePos(); return tY;}
    public double getZTranslation() {updatePos(); return tZ;}

    public double getXRotation() {updatePos(); return rX;}
    public double getYRotation() {updatePos(); return rY;}
    public double getZRotation() {updatePos(); return rZ;}

    public RelicRecoveryVuMark getColumn() {updatePos(); return vuMark;}
}
