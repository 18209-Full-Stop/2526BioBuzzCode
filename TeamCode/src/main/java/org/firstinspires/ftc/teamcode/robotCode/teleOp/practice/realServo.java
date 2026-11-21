package org.firstinspires.ftc.teamcode.robotCode.teleOp.practice;

import com.pedropathing.follower.Follower;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.robotCode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.robotCode.subsystems.FlyWheel;

import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class realServo extends OpMode{
    //Variables

    //Create follower
    private Follower follower;

    //Motors
    public Servo necGate;

    @Override
    public void loop() {
        //Intake
        if (gamepad2.left_bumper) { //left bumper gets artifacts out (brings them down)
            necGate.setPosition(0.6);
        } else if (gamepad2.right_bumper) { //right bumper puts them up
            necGate.setPosition(0.4);
        }
        telemetry.update();
    }

    @Override
    public void init() {
        //Ivy commands reset
        Scheduler.reset();

        //Motor initialization
        necGate = hardwareMap.get(Servo.class, "necGate");

//        follower = Constants.create(hardwareMap);

        Scheduler.execute();

    }

}
