package org.firstinspires.ftc.teamcode.robotCode.autos;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
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
public class testBLUE extends OpMode {
    //Motors
    public Intake intake;

    //Variables

    //Create follower
    private Follower follower;

    //Create PedroPathing poses
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose startPose = poseFactory.of(39.3311, 11.0999, 90);
    private final Pose path1 = poseFactory.of(66.6607, 33.9607, 180);
    private final Pose point2 = poseFactory.of(13.0042, 34.4613, -0.5345);

    //All paths
    public Path path1() {
        return line(startPose, path1).linear(startPose, path1);
    }
    public Path path2() {return line(path1, point2).reverseTangent();}

    //Runs the auto
    private Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                race(
                    intake.intakeSetPower(1.0),
                    follow(follower, path2())
                ),
                intake.refill()
        );
    }



    //Override statements
    @Override
    public void init() {
        //Motors
        intake = new Intake(hardwareMap);

        //PedroPathing
        Scheduler.reset();
        //follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
    }

    @Override
    public void start() {
        schedule(autoRoutine());
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }

}
