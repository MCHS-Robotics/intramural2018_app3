package org.firstinspires.ftc.teamirrelevant;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by student on 3/21/18.
 */

public class Arm {

    DcMotor elbow;

    Servo wrist,hand;

    float[] handPos = {0,1}; //{open,closed}

    int elbowPos = 1000; //maximum movement

    float wristPos = .5f; //resting

    float[] wristExt = {0,1};

    int elbowMovePos;

    float elbowPow;

    /**
     * Creates an arm object to manipulate the noodles
     * @param map the hardware map of the robot
     */
    public Arm(HardwareMap map){
        elbow = map.dcMotor.get("elbow");
        wrist = map.servo.get("wrist");
        hand = map.servo.get("hand");
    }

    /**
     * Open the claw of the robot
     */
    public void openHand(){
        hand.setPosition(handPos[0]);
    }

    /**
     * Closes the claw of the robot
     */
    public void closeHand(){
        hand.setPosition(handPos[1]);
    }

    /**
     * Sets the power of the elbow of the robot
     * @param power the power to move the elbow at
     */
    public void setElbowPower(float power){
        elbow.setPower(power);
    }

    /**
     * moves the elbow to a certain motor encoder position
     * @param pos the motor encoder position of the elbow
     * @param power the power to move the elbow at
     */
    public void runElbowTo(int pos,float power){
        elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elbow.setTargetPosition(pos);
        elbow.setPower(power);
        elbowMovePos = pos;
        elbowPow = power;
    }

    /**
     * checks if the elbow has reached its target position
     * @preconditon runElbowTo has been called
     */
    public void checkElbow(){
        if(elbow.getCurrentPosition() > elbowPos)
            runElbowTo(elbowPos,.1f);
        if(elbow.getCurrentPosition() < 0)
            runElbowTo(0,.1f);
        if(!elbow.isBusy()){
            elbow.setPower(0);
        }
    }

    /**
     * checks if the elbow has reached its target position
     * @preconditon runElbowTo has been called
     * @param maxError the maximum error allowed when stopping
     */
    public void checkElbow(int maxError){
        if(elbow.getCurrentPosition() > elbowPos)
            runElbowTo(elbowPos,.1f);
        if(elbow.getCurrentPosition() < 0)
            runElbowTo(0,.1f);
        if(!elbow.isBusy()){
            if(Math.abs(elbowMovePos-elbow.getCurrentPosition()) > maxError){
                elbow.setTargetPosition(elbowMovePos);
                elbowPow/=2;
                elbow.setPower(elbowPow);
            }
            else elbow.setPower(0);
        }
    }

    /**
     * Moves the wrist to a resting position
     */
    public void restWrist(){
        wrist.setPosition(wristPos);
    }

    /**
     * Moves the wrist up some amount
     * @param amt the amount the wrist should move up
     */
    public void moveWristUp(float amt){
        if(wrist.getPosition() >= wristExt[1])
            wrist.setPosition(wrist.getPosition()+amt);
    }

    /**
     * Moves the wrist down some amount
     * @param amt the amount the wrist should move down
     */
    public void moveWristDown(float amt){
        if(wrist.getPosition() <= wristExt[0])
            wrist.setPosition(wrist.getPosition()-amt);
    }

    /**
     * Gives telem data on the arm of the robot
     */
    public void getTelemData(Telemetry t){
        t.addData("WristPosition",wrist.getPosition());
        t.addData("ClawPosition",wrist.getPosition());
        t.update();
    }
}

