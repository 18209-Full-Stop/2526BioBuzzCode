package org.firstinspires.ftc.teamcode.robotCode.teleOp.forComp;

import com.pedropathing.follower.Follower;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.robotCode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.robotCode.subsystems.FlyWheel;

import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.repeat;

@TeleOp
public class testRED extends OpMode{
    //Variables
    private double polSpeed = -0.7;
    private double necSpeed = -0.7;

    //Create follower
    private Follower follower;

    //Motors
    public Intake intake;
    public FlyWheel flyWheel;

    @Override
    public void loop() {
        //Telemetry info
        telemetry.addData("polSpeed", polSpeed);
        telemetry.addData("necSpeed", necSpeed);

        //Intake
        if (gamepad2.left_trigger >= 0.5) { //left bumper gets artifacts out (brings them down)
            intake.setPower(1);
        } else if (gamepad2.right_trigger >= 0.5) { //right bumper puts them up
            intake.setPower(-1);
        } else { //otherwise turns intake off
            intake.setPower(0);
        }

        //REAL pollen code for launching
//        if (gamepad2.x) { //runs pollen flywheel
//            schedule(flyWheel.workLaunch(1,1.5,false,true));
//        }
//
//        //REAL nectar code for launching
//        if (gamepad2.b) { //runs pollen flywheel
//            schedule(flyWheel.workLaunch(1,1.5,true,false));
//        }

        //FlyWheel
//        if (gamepad2.x) { //runs pollen flywheel
//            flyWheel.setPowerPol(1);
//        } else { //turns off
//            flyWheel.setPowerPol(0);
//        }

//        if (gamepad2.b) { //runs nectar flywheel
//            flyWheel.setPowerNec(1);
//        } else { //turns off
//            flyWheel.setPowerNec(0);
//        }

        //Pollen code for gate
//        if (gamepad2.left_trigger >= 0.5){
//            flyWheel.openPol();
//        } else if(!flyWheel.isOpenPol){
//            flyWheel.closePol();
//        }

        //Nectar code for gate
//        if (gamepad2.right_trigger >= 0.5){
//            flyWheel.openNec();
//        } else if(!flyWheel.isOpenNec){
//            flyWheel.closeNec();
//        }

        //Manual code to run flywheel nectar
        if (gamepad2.x){
            flyWheel.setPowerNec(necSpeed);
        } else if (!flyWheel.isLaunchingNec){
            flyWheel.setPowerNec(0);
        }

        //Manual code to run flywheel pollen
        if (gamepad2.b){
            flyWheel.setPowerPol(polSpeed);
        } else if (!flyWheel.isLaunchingPol){
            flyWheel.setPowerPol(0);
        }

        //Resets heading when pressed
        if (gamepad1.left_bumper){
            follower.setPose(new Pose(follower.pose().x(), follower.pose().y(), 0));
        }

        //Increments speed of pollen flywheel
        if (gamepad2.dpadUpWasPressed() && !(polSpeed >= 1.0)) {
            polSpeed += 0.1;
        } else if(gamepad2.dpadDownWasPressed() && !(polSpeed <= 0.1)){
            polSpeed -= 0.1;
        }

        //Increments speed of nectar flywheel
        if (gamepad2.dpadRightWasPressed() && !(necSpeed >= 1.0)) {
            necSpeed += 0.1;
        } else if(gamepad2.dpadLeftWasPressed() && !(necSpeed <= 0)){
            necSpeed -= 0.1;
        }

        //resets necSpeed
        if (gamepad2.y){
            necSpeed = 0.7;
        } else if (gamepad2.a){ //resets polSpeed
            polSpeed = 0.7;
        }

        if (gamepad2.right_bumper){
            intake.setPolPos(0.2);
        } else {
            intake.setPolPos(0.5);
        }

        if (gamepad2.left_bumper){
            intake.setNecPos(0.2);
        } else {
            intake.setNecPos(0.5);
        }

        //PedroPathing drive
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x,
                //added the 90 degrees rotation 4 red alliance
                follower.pose().heading()
        );
        ManualDrive.driveOrHold(follower, powers);
        follower.update();
    }

    @Override
    public void init() {
        //Ivy commands reset
        Scheduler.reset();

        //Motor initialization
        intake = new Intake(hardwareMap);
        flyWheel = new FlyWheel(hardwareMap);

        follower = Constants.create(hardwareMap);

        //Runs commands; commented out for now
//        Scheduler.execute();
    }

}
