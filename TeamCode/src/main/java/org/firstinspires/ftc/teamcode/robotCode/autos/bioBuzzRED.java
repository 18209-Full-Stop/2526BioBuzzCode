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
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robotCode.subsystems.Intake;

@Autonomous
public class bioBuzzRED extends LinearOpMode {
    //Motors
    public Intake intake;

    //Variables

    //Create follower
    private Follower follower;

    //Create PedroPathing poses
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose startingPoint = poseFactory.of(8.684, 79.7164, 90);
    private final Pose path1Start = poseFactory.of(8.684, 79.7164, 0);
    private final Pose path1 = poseFactory.of(59.0506, 29.6801, 90);
    private final Pose path1Control1 = poseFactory.of(20.8286, 39.9437, 0);


    @Override
    public void runOpMode(){
        intake = new Intake(hardwareMap);
        Scheduler.reset();
        //follower = Constants.create(hardwareMap);
        follower.setPose(startingPoint);
        follower.update();

        Command autoRoutine = sequential(
                follow(follower, path1()),
                intake.refill()
        );

        waitForStart();
        schedule(autoRoutine);

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

    public Path path1() {
        return curve(path1Start, path1Control1, path1).linear(path1Start, path1);
    }

}
