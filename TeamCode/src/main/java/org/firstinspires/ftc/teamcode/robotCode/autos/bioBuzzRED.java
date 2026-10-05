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
    private final Pose point2 = poseFactory.of(15.9655, 46.4563, 180);
    private final Pose point3 = poseFactory.of(59.0506, 29.6801, 90);
    private final Pose point4Start = poseFactory.of(59.0506, 29.6801, 90);
    private final Pose point4 = poseFactory.of(8.9908, 103.3017, 0);
    private final Pose point4Control1 = poseFactory.of(8.4134, 62.4563, 0);


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

        //Commands to follow path1, then launch
        Command autoRoutine = sequential(
                follow(follower, path1()),
                repeat(launchRefill, 4),
                follow(follower, path2()),
                intake.intakeSetPowerFor(1,2),
                follow(follower, path3()),
                repeat(launchRefill, 4),
                follow(follower, path4())
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

    public Path path2() {
        return line(path1, point2).linear(path1, point2);
    }

    public Path path3() {
        return line(point2, point3).linear(point2, point3);
    }

    public Path path4() {
        return curve(point3, point4Control1, point4).linear(point3, point4);
    }

}
