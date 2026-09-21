package org.firstinspires.ftc.teamcode.robotCode.teleOp;

import com.pedropathing.follower.Follower;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.robotCode.subsystems.Intake;

@TeleOp
public class BLUE extends OpMode{

    //Create follower
    private Follower follower;

    //Motors
    public Intake intake;

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

        //PedroPathing drive
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x,
                follower.pose().heading()
        );
        ManualDrive.driveOrHold(follower, powers);
        follower.update();
    }

    @Override
    public void init() {
        //Motor initialization
        intake = new Intake(hardwareMap);

        //follower = Constants.create(hardwareMap);
    }

}
