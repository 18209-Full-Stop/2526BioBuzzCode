package org.firstinspires.ftc.teamcode.robotCode.teleOp;

import com.pedropathing.follower.Follower;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.robotCode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.robotCode.subsystems.FlyWheel;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;


@TeleOp
public class BLUEwithApril extends OpMode{

    //Create follower
    private Follower follower;

    //Motors
    public Intake intake;
    public FlyWheel flyWheel;

    //AprilTag implementation
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    //PD controller
    double kP = 0.002;
    double error = 0;
    double lastError = 0;
    double goalX = 0;
    double angleTolerance = 0.4;
    double kD = 0.0001;
    double curTime = 0;
    double lastTime = 0;
    double forward, strafe, rotate;
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

        //FlyWheel
        if (gamepad2.x) { //runs pollen flywheel
            flyWheel.setPowerPol(1);
        } else { //turns off
            flyWheel.setPowerPol(0);
        }

        if (gamepad2.b) { //runs nectar flywheel
            flyWheel.setPowerNec(1);
        } else { //turns off
            flyWheel.setPowerNec(0);
        }

        if (gamepad2.left_trigger >= 0.5){
            flyWheel.openPol();
        } else{
            flyWheel.closePol();
        }

        if (gamepad2.right_trigger >= 0.5){
            flyWheel.openNec();
        } else{
            flyWheel.closeNec();
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

        visionPortal.resumeStreaming();
    }

    @Override
    public void init() {
        //Motor initialization
        intake = new Intake(hardwareMap);
        flyWheel = new FlyWheel(hardwareMap);

        initAprilTag();

        //follower = Constants.create(hardwareMap);
    }

    private void initAprilTag() {

        // Create the AprilTag processor the easy way.
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.

        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);


    }

}
