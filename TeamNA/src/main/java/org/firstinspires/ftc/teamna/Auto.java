package org.firstinspires.ftc.teamna;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Team NAAuto", group="Linear Opmode")
public class Auto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        DcMotor left1 = hardwareMap.get(DcMotor.class, "fl");
        DcMotor left2 = hardwareMap.get(DcMotor.class, "bl");
        DcMotor right = hardwareMap.get(DcMotor.class, "fr");

        left1.setPower(0.4);
        left2.setPower(0.4);
        right.setPower(0.4);
        wait(3000);
        left1.setPower(0);
        left2.setPower(0);
        right.setPower(0);
    }
}
