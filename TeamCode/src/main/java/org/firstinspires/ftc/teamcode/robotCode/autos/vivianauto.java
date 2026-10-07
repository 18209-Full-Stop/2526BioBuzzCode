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
import static com.pedropathing.ivy.groups.Groups.repeat;
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

    private final Pose start = poseFactory.of(8.6214, 16.473, 90);
    private final Pose point1Start = poseFactory.of(8.6214, 16.473, 180);
    private final Pose point1 = poseFactory.of(23.6806, 31.2612, 102.512);
    private final Pose point2 = poseFactory.of(134.529, 31.7244, -179.7606);
    private final Pose point3 = poseFactory.of(11.1141, 46.1683, -6.6753);


    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                follow(follower, path3()),
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
        Command launchRefill = sequential(
                intake.refill()
        );

        //Commands to follow path1, then launch
        Command autoRoutine = sequential(
                follow(follower, path1()),
                repeat(launchRefill, 3),
                follow(follower, path2()),
                intake.intakeSetPowerFor(1,3),
                follow(follower, path3()),
                repeat(launchRefill, 3)
        );

        //Wait, then begin
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


    //Define all paths HERE
    public Path path1() {
        return line(point1Start, point1).linear(point1Start, point1);
    }

    public Path path2() {
        return line(point1, point2).reverseTangent();
    }

    public Path path3() {
        return line(point2, point3).reverseTangent();
    }


}
