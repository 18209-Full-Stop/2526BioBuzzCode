package org.firstinspires.ftc.teamcode.robotCode.autos;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.race;
import static com.pedropathing.ivy.groups.Groups.repeat;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

//import static org.opencv.core.Core.repeat;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robotCode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.robotCode.subsystems.FlyWheel;

@Autonomous
public class bioBuzzRED extends LinearOpMode {
    //Motors
    public Intake intake;
    public FlyWheel flyWheel;

    //Create follower
    private Follower follower;

    //PedroPathing poses
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose startingPoint = poseFactory.of(8.684, 79.7164, 90);
    private final Pose path1Start = poseFactory.of(8.684, 79.7164, 0);
    private final Pose path1 = poseFactory.of(59.0506, 29.6801, 90);
    private final Pose path1Control1 = poseFactory.of(20.8286, 39.9437, 0);


    @Override
    public void runOpMode(){
        //Initialize
        intake = new Intake(hardwareMap);
        flyWheel = new FlyWheel(hardwareMap);

        //Set up program
        Scheduler.reset();
        //follower = Constants.create(hardwareMap); //Uncomment this later on!!!
        follower.setPose(startingPoint);
        follower.update();

        //Launch then refill
        Command launchRefill = sequential(
                flyWheel.launch(false),
                intake.refill()
        );

        //Compiles commands to follow path1, then launch
        Command autoRoutine = sequential(
                follow(follower, path1()),
                repeat(launchRefill, 4)
        );

        //Wait, then begin
        waitForStart();
        schedule(autoRoutine);

        //Execute scheduler, update follower and telemetry
        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    //All paths
    public Path path1() {
        return curve(path1Start, path1Control1, path1).linear(path1Start, path1);
    }

}
