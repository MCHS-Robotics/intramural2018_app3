package org.firstinspires.ftc.teamirrelevant;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public interface MoveableRobot {

    //Creation

    void init(HardwareMap hardwareMap);

    //Tele

    void run(Gamepad gamepad1,Gamepad gamepad2);

    //Auto

    void forward(float inches);

    void backward(float inches);

    void left(float inches);

    void right(float inches);

    void clockwise(int degrees);

    void cClockwise(int degrees);

    void testDrive(int seconds);
}

