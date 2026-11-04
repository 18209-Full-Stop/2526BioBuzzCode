package org.firstinspires.ftc.teamcode.robotCode.autos;
import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.repeat;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.robotCode.subsystems.Intake;
import com.pedropathing.follower.Follower;

@Autonomous
public class mayaTest extends LinearOpMode{
    //Motors

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    //Define path points HERE
    private final Pose start = poseFactory.of(127.2333, 7.8489, 90);
    private final Pose path1 = poseFactory.of(125.9911, 82.0575, 90);
    private final Pose point2Start = poseFactory.of(125.9911, 82.0575, 90);
    private final Pose point2 = poseFactory.of(73.778, 121.0005, 0);
    private final Pose point2Control1 = poseFactory.of(108.536, 136.9119, 0);
    private final Pose point3Start = poseFactory.of(73.778, 121.0005, 0);
    private final Pose point3 = poseFactory.of(122.3163, 115.1741, 0);
    private final Pose point4Start = poseFactory.of(122.3163, 115.1741, 0);
    private final Pose point4 = poseFactory.of(127.2333, 7.8489, 90);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                follow(follower, path3()),
                follow(follower, path4())
        );
    }

    @Override
    public void runOpMode() {
        //Initialize
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        //Wait, then begin
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
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.curve(point2Start, point2Control1, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.line(point3Start, point3).linear(point3Start, point3);
    }

    public Path path4() {
        return Paths.line(point4Start, point4).linear(point4Start, point4);
    }
}
