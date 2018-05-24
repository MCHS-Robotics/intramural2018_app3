package org.firstinspires.ftc.teamirrelevant;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaNavigation;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static java.lang.Thread.sleep;

public class Vuforia implements Runnable{

    VuforiaLocalizer vuforia;
    VuforiaTrackables relicTrackable;
    Telemetry telemetry;
    boolean run = true;
    /**
     * Creates a Vuforia object
     * @param hardwareMap The hardware map of the robot
     */
    public Vuforia(HardwareMap hardwareMap, Telemetry telemetry) {

        this.telemetry = telemetry;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.cameraMonitorFeedback= VuforiaLocalizer.Parameters.CameraMonitorFeedback.BUILDINGS;


        parameters.vuforiaLicenseKey = "Acbhfjv/////AAAAGQwVGIAmv0V3nNH7nrtPGJVSuirk278Uo+j+394hRfZGLNmucewzhlA4ux8ZUQz0OpL1mJuPW+lKbjippfmpGiiXsvpnm2p6prlsNHzZNthjThZyFArOkXDkjL5bYlVT3zlo3oc+XNg/W4rBdXHeBpw6OgO1a7D0xhGHkqaihUWqeESvWtcH+uSJXha/umtRGu0DIPRW0n4a3Z0cjbNzhicHvrGOWuwuVhyua1IcrOqed3/bTdts+JhuG3TakwFBb1GpIkWXNziiorUUXVstPVNVrty3AnesNZ11gsJyHVu3YCKi//itjkqr/6xmINr4LDrwdf1Pg2e9L9iT9qtFWjrrnEQYGzxgpnrj0+PvzWWw";


        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        relicTrackable = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
    }

    /**
     * Activates the relicTrackables for vueforia
     */
    public void activate(){
        run = true;
        relicTrackable.activate();
    }

    /**
     * Deactivates the relicTrackables for vueforia
     */
    public void deactivate(){
        relicTrackable.deactivate();
        run = false;
    }

    /**
     * Gets which template is up
     * @return 0 = none, 1 = left, 2 = right, 3 = center
     */
    public int getPos(){
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTrackable.get(0));
        if (vuMark == RelicRecoveryVuMark.CENTER) {
            return 3;
        }
        if (vuMark == RelicRecoveryVuMark.LEFT) {
            return 1;
        }
        if (vuMark == RelicRecoveryVuMark.RIGHT) {
            return 2;
        }
        return 0;
    }

    /**
     * Shows which markers are visible
     * @param telemetry
     */
    public void getTelemetryData(Telemetry telemetry) {

        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTrackable.get(0));
        if (vuMark == RelicRecoveryVuMark.CENTER) {
            telemetry.addData("VueMark", "Center Visible");
        } else {

            telemetry.addData("VueMark", "Center not Visible");
        }
        if (vuMark == RelicRecoveryVuMark.LEFT) {
            telemetry.addLine();
            telemetry.addData("VueMark", "Left Visible");
        } else {
            telemetry.addLine();
            telemetry.addData("VueMark", "Left not Visible");
        }
        if (vuMark == RelicRecoveryVuMark.RIGHT) {
            telemetry.addLine();
            telemetry.addData("VueMark", "Right Visible");
        } else {
            telemetry.addLine();
            telemetry.addData("VueMark", "Right not Visible");
        }
        telemetry.update();
    }

    public void run(){
        while (run) {
            getTelemetryData(telemetry);
            try {
                sleep(100);
            }catch (Exception e){}
        }
    }
}