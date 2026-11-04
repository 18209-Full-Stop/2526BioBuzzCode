package org.firstinspires.ftc.teamcode.robotCode.teleOp;

import com.pedropathing.follower.Follower;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.robotCode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.robotCode.subsystems.FlyWheel;
import org.firstinspires.ftc.teamcode.robotCode.subsystems.AprilTags;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.repeat;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;


@TeleOp
public class BLUE extends OpMode{
    //Variables
    private boolean autoAlign = false;

    //Create follower
    private Follower follower;

    //Motors
    public Intake intake;
    public FlyWheel flyWheel;
    public AprilTags aprilTags;

    @Override
    public void loop() {
        //Intake
        if (gamepad2.left_bumper) { //left bumper gets artifacts out (brings them down)
            intake.setPower(-1);
        } else if (gamepad2.right_bumper) { //right bumper puts them up
            intake.setPower(1);
        } else { //otherwise turns intake off
            intake.setPower(0);
        }

        //REAL pollen code for launching
        if (gamepad2.x) { //runs pollen flywheel
            schedule(flyWheel.workLaunch(1,1.5,false,true));
        }

        //REAL nectar code for launching
        if (gamepad2.b) { //runs pollen flywheel
            schedule(flyWheel.workLaunch(1,1.5,true,false));
        }

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
        if (gamepad2.left_trigger >= 0.5){
            flyWheel.openPol();
        } else if(!flyWheel.isOpenPol){
            flyWheel.closePol();
        }

        //Nectar code for gate
        if (gamepad2.right_trigger >= 0.5){
            flyWheel.openNec();
        } else if(!flyWheel.isOpenNec){
            flyWheel.closeNec();
        }

        //Manual code to run flywheel nectar
        if (gamepad2.y){
            flyWheel.setPowerNec(1.0);
        } else if (!flyWheel.isLaunchingNec){
            flyWheel.setPowerNec(0);
        }

        //Manual code to run flywheel pollen
        if (gamepad2.a){
            flyWheel.setPowerPol(1.0);
        } else if (!flyWheel.isLaunchingPol){
            flyWheel.setPowerPol(0);
        }

        //Decides whether to use auto-align
        if (gamepad1.left_bumper){
            autoAlign = true;
        } else if (gamepad1.right_bumper){
            autoAlign = false;
        }

        //PedroPathing drive
        if (!autoAlign) {
            DrivePowers powers = ManualDrive.fieldCentric(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x,
                    follower.pose().heading()
            );
            ManualDrive.driveOrHold(follower, powers);
            follower.update();
        } else {
            aprilTags.calcVals();
        }
    }

    @Override
    public void init() {
        //Ivy commands reset
        Scheduler.reset();

        //Motor initialization
        intake = new Intake(hardwareMap);
        flyWheel = new FlyWheel(hardwareMap);
        aprilTags = new AprilTags(hardwareMap);

        follower = Constants.create(hardwareMap);

        //Runs commands
        Scheduler.execute();
    }

}
