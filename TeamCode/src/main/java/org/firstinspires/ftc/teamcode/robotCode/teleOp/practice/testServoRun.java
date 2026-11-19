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

@TeleOp
public class testServoRun extends OpMode{
    //Variables
    private double polSpeed = 1.0;
    private double necSpeed = 1.0;

    //Create follower
    private Follower follower;

    //Motors
    public Intake intake;
    public FlyWheel flyWheel;

    @Override
    public void loop() {
        //Intake
        if (gamepad2.left_bumper) { //left bumper gets artifacts out (brings them down)
            intake.necGateOpen();
        } else if (gamepad2.right_bumper) { //right bumper puts them up
            intake.necGateClose();
        }

        if (gamepad2.x){
            intake.intakeSetPower(1);
        } else {
            intake.intakeSetPower(0);
        }
        telemetry.update();
    }

    @Override
    public void init() {
        //Ivy commands reset
        Scheduler.reset();

        //Motor initialization
        intake = new Intake(hardwareMap);
        flyWheel = new FlyWheel(hardwareMap);

        follower = Constants.create(hardwareMap);

        Scheduler.execute();

    }

}
