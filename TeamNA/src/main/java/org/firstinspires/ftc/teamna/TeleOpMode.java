package org.firstinspires.ftc.teamna;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Team NATeleOp", group="Linear Opmode")
public class TeleOpMode extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();


    /**
     * Runs ingenious teleop code that scores on average #{Game.MAX_TELEOP_POINTS} points
     * @precondion  Robot Works
     * @postcondion Win Competition
     */
    @Override
    public void runOpMode() {
        DcMotor left2, left1, right2,
                endgame;
        //Servo   auto, claw   ;

        left1     = hardwareMap.get(DcMotor.class, "fl"  );
        left2     = hardwareMap.get(DcMotor.class, "bl");
        //right1   = hardwareMap.get(DcMotor.class, "fr"  );
        right2   = hardwareMap.get(DcMotor.class, "fr"  );
        //arm     = hardwareMap.get(DcMotor.class, "arm" );
        endgame  = hardwareMap.get(DcMotor.class, "end" );
        //auto    = hardwareMap.get(Servo  .class, "auto");
        //claw    = hardwareMap.get(Servo  .class, "claw");

        left1    .setDirection(DcMotor.Direction.FORWARD);
        left2    .setDirection(DcMotor.Direction.FORWARD);
        right2   .setDirection(DcMotor.Direction.REVERSE);

        waitForStart  ();
        runtime .reset();

        while (opModeIsActive()) {
            left1    .setPower   (-gamepad1. left_stick_y);
            left2   .setPower   (-gamepad1.left_stick_y);
            right2  .setPower   (-gamepad1.right_stick_y);
            //arm    .setPower   (-gamepad2.right_stick_y);
            endgame.setPower   (gamepad1.a ? 0.5 : -0.5);
            //auto   .setPosition( gamepad2. a ? 1  :  0 );
            //claw   .setPosition( gamepad2. b ? 1  :  0 );
        }
    }
}