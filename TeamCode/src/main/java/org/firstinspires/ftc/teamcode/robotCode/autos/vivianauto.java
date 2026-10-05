package org.firstinspires.ftc.teamcode.robotCode.autos;
import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotCode.subsystems.Intake;

@Autonomous
public class vivianauto extends LinearOpMode{
    //Motors
    public Intake intake;

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    //Define path points HERE

    private final Pose start = poseFactory.of(9.4266, 110.0461, 90);
    private final Pose path1 = poseFactory.of(22.479, 33.6268, 180);
    private final Pose path2 = poseFactory.of(132.8847, 35.914, 180);
    private final Pose path3 = poseFactory.of(127.5257, 112.2874, -85.9862);
    private final Pose path4 = poseFactory.of(5.8279, 109.8221, 1.1605);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                intake.intakeSetPower(1)
        );
    }

    @Override
    public void runOpMode() {
        //Initialize
        intake = new Intake(hardwareMap);

        Scheduler.reset();
        //follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        waitForStart();
        schedule(autoRoutine());

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


    //Define all paths HERE
    public Path path1() {
        return line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return line(path1, path2).linear(path1, path2);
    }



}
