package org.firstinspires.ftc.teamirrelevant;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static java.lang.Thread.sleep;

public class NormalDrive implements MoveableRobot {

    DcMotor L,R;
    float y,x2,deadZone,turnPower,movePower;

    double Dr;
    double Cr;
    double Dw;
    double Cw;
    int Mstep;

    //Creation

    /**
     * Creates a basic normal drive for the robot with set defaults
     * @param    hardwareMap the hardware map of the robot for initialization
     */
    public NormalDrive(HardwareMap hardwareMap){
        deadZone = 0;
        turnPower = 1;
        movePower = 1;

        Dr = 19;
        Cr = Math.PI * Dr;
        Dw = 4;
        Cw = Math.PI * Dw;
        Mstep = 1120;

        init(hardwareMap);
    }

    /**
     * Creates a normal drive for the robot with variables for the teleop
     * @param    deadZone    the radius (from 0-1) of the deadzone from the gamepad
     * @param    turnPower   scaling modifier for the turning power of the robot
     * @param    movePower   scaling modifier for the translation movement power of the robot
     * @param    hardwareMap the hardware map of the robot for initialization
     */
    public NormalDrive(float deadZone,float turnPower,float movePower,HardwareMap hardwareMap){
        this.deadZone = deadZone;
        this.turnPower = turnPower;
        this.movePower = movePower;

        Dr = 19;
        Cr = Math.PI * Dr;
        Dw = 4;
        Cw = Math.PI * Dw;
        Mstep = 1120;

        init(hardwareMap);
    }

    /**
     * Creates a normal drive for the robot with variables for the autonomous
     * @param    robotDiameter   the length, in inches, between 2 diagonal wheels
     * @param    wheelDiameter   the diameter, in inches, of the wheels used on the robot
     * @param    motorControllerSteps    the amount of ticks the motors have in one revolution
     * @param    hardwareMap the hardware map of the robot for initialization
     */
    public NormalDrive(double robotDiameter,double wheelDiameter,int motorControllerSteps,HardwareMap hardwareMap){
        deadZone = 0;
        turnPower = 1;
        movePower = 1;

        Dr = robotDiameter;
        Cr = Math.PI * Dr;
        Dw = wheelDiameter;
        Cw = Math.PI * Dw;
        Mstep = motorControllerSteps;

        init(hardwareMap);
    }

    /**
     * Initializes the motors for the robot
     * @param    hardwareMap the hardware map of the robot for initialization
     */
    public void init(HardwareMap hardwareMap){
        L = hardwareMap.dcMotor.get("l");
        R = hardwareMap.dcMotor.get("r");
        R.setDirection(DcMotor.Direction.REVERSE);
    }

    //Tele

    /**
     * moves the robot in teleop using the 2 thumbsticks
     * Left joystick: forwards and backwards
     * Right joystick: turning
     * @param    gamepad1 the first controller being used for the robot
     * @param    gamepad2 the second controller being used for the robot
     */
    public void run(Gamepad gamepad1, Gamepad gamepad2){
        y = gamepad1.left_stick_y;
        if(Math.abs(y) < deadZone)y = 0;
        x2 = gamepad1.right_stick_x;
        if(Math.abs(x2) < deadZone)x2 = 0;
        L.setPower(Math.abs(y) * y * movePower + Math.abs(x2) * x2 * turnPower);
        R.setPower(Math.abs(y) * y * movePower - Math.abs(x2) * x2 * turnPower);
    }

    //Auto

    /**
     * moves the robot forward a variable amount of inches
     * @param    inches the distance, in inches, the robot moves
     */
    public void forward(float inches){
        int pos = (int)(inches * Mstep / Cw);
        L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        R.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        L.setTargetPosition(pos);
        R.setTargetPosition(pos);
        L.setPower(0.3);
        R.setPower(0.3);
        while(L.isBusy()&&R.isBusy()) {
        }
        L.setPower(0);
        R.setPower(0);
        L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        R.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * moves the robot backward a variable amount of inches
     * @param    inches the distance, in inches, the robot moves
     */
    public void backward(float inches){
        int pos = -(int)(inches * Mstep / Cw);
        L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        R.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        L.setTargetPosition(pos);
        R.setTargetPosition(pos);
        L.setPower(0.3);
        R.setPower(0.3);
        while(L.isBusy()&&R.isBusy()) {
        }
        L.setPower(0);
        R.setPower(0);
        L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        R.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    /**
     * moves the robot left a variable amount of inches
     * @param    inches the distance, in inches, the robot moves
     */
    public void left(float inches){
        cClockwise(90);
        forward(inches);
        clockwise(90);
    }

    /**
     * moves the robot right a variable amount of inches
     * @param    inches the distance, in inches, the robot moves
     */
    public void right(float inches){
        clockwise(90);
        forward(inches);
        cClockwise(90);
    }

    /**
     * turns the robot clockwise a variable amount of degrees
     * @param    degrees the amount of degrees the robot turns
     */
    public void clockwise(int degrees){
        int pos = (int)(degrees * Cr * Math.sqrt(2) * Mstep / 360 / Cw);
        L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        L.setTargetPosition(pos);
        R.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        R.setTargetPosition(-pos);
        L.setPower(0.3);
        R.setPower(0.3);
        while(L.isBusy() && R.isBusy()){
        }
        L.setPower(0);
        R.setPower(0);
        L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        R.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * turns the robot counter clockwise a variable amount of degrees
     * @param    degrees the amount of degrees the robot turns
     */
    public void cClockwise(int degrees){
        int pos = (int)(degrees * Cr * Math.sqrt(2) * Mstep / 360 / Cw);
        L.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        L.setTargetPosition(-pos);
        R.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        R.setTargetPosition(pos);
        L.setPower(0.3);
        R.setPower(0.3);
        while(L.isBusy() && R.isBusy()){
        }
        L.setPower(0);
        R.setPower(0);
        L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        R.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * tests robot to make sure it is in working order by rotating each wheel forward for a variable amt of time
     * @param    seconds time spent moving motors
     */
    public void testDrive(int seconds){
        try {
            L.setPower(0.5);
            wait(seconds * 1000);
            L.setPower(0);
            R.setPower(0.5);
            wait(seconds * 1000);
            R.setPower(0);
        }catch(Exception e){}
    }

    /**
     * Moves the robot forward for some milliseconds
     * @param milliseconds The time the robot moves
     */
    public void ForwardTime(int milliseconds){
        L.setPower(0.5);
        R.setPower(0.5);
        try {
            sleep(milliseconds);
        }catch (Exception e){}
        L.setPower(0);
        R.setPower(0);
    }
}

