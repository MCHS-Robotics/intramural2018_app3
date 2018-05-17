package org.firstinspires.ftc.teamirrelevant;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class ColorSense {

    com.qualcomm.robotcore.hardware.ColorSensor colorSensor;
    /**
     * creates a ColorSense object
     * @param hardwareMap hardware map on the phone
     * @param deviceName the name the color sensor is mapped to
     */
    public ColorSense(HardwareMap hardwareMap, String deviceName) {
        colorSensor = hardwareMap.colorSensor.get(deviceName);
    }

    /**
     * turns led on
     */
    public void on(){
        colorSensor.enableLed(true);
    }

    /**
     * turns led off
     */
    public void off(){
        colorSensor.enableLed(false);
    }

    /**
     * returns RGB color for colorSensor
     */
    public void colorStats(Telemetry telemetry){
        telemetry.addData("Blue:",colorSensor.blue());
        telemetry.addLine();
        telemetry.addData("Green:",colorSensor.green());
        telemetry.addLine();
        telemetry.addData("Red:",colorSensor.red());
        telemetry.update();
    }

    /**
     * returns if the object is red
     * @return true if object is more red then blue
     */
    public boolean isRed(){
        boolean isRed = colorSensor.red() > colorSensor.blue();
        return isRed;
    }

}

