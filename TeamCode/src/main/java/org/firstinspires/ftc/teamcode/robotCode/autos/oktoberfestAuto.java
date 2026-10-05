import com.pedropathing.api.Paths;
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

@Autonomous
public class oktoberfestAuto extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(72, 36, 90);
    private final Pose diamond1 = poseFactory.of(108, 72, 45);
    private final Pose diamond2 = poseFactory.of(72, 108, 135);
    private final Pose diamond3 = poseFactory.of(36, 72, -135);
    private final Pose diamond4 = poseFactory.of(72, 36, -45);
    private final Pose circle1 = poseFactory.of(108, 72, 270);
    private final Pose circle1Control1 = poseFactory.of(91.92, 36, 0);
    private final Pose circle1Control2 = poseFactory.of(108, 52.08, 0);
    private final Pose circle2 = poseFactory.of(72, 108, 270);
    private final Pose circle2Control1 = poseFactory.of(108, 91.92, 0);
    private final Pose circle2Control2 = poseFactory.of(91.92, 108, 0);
    private final Pose circle3 = poseFactory.of(36, 72, 270);
    private final Pose circle3Control1 = poseFactory.of(52.08, 108, 0);
    private final Pose circle3Control2 = poseFactory.of(36, 91.92, 0);
    private final Pose circle4 = poseFactory.of(72, 36, 270);
    private final Pose circle4Control1 = poseFactory.of(36, 52.08, 0);
    private final Pose circle4Control2 = poseFactory.of(52.08, 36, 0);
    private final Pose star1 = poseFactory.of(90, 90, 0);
    private final Pose star2 = poseFactory.of(36, 72, 90);
    private final Pose star3 = poseFactory.of(90, 54, 180);
    private final Pose star4 = poseFactory.of(72, 108, 270);
    private final Pose star5 = poseFactory.of(54, 54, 0);
    private final Pose star6 = poseFactory.of(108, 72, 90);
    private final Pose star7 = poseFactory.of(54, 90, 180);
    private final Pose star8 = poseFactory.of(72, 36, 270);
    private final Pose reset = poseFactory.of(72, 36, 45);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                follow(follower, path3()),
                follow(follower, reset())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        // follower = Constants.create(hardwareMap);
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

    public Path path1() {
        return Paths.path(Paths.line(start, diamond1).tangent(), Paths.line(diamond1, diamond2).tangent(), Paths.line(diamond2, diamond3).tangent(), Paths.line(diamond3, diamond4).tangent());
    }

    public Path path2() {
        return Paths.path(Paths.curve(diamond4, circle1Control1, circle1Control2, circle1).constant(circle1), Paths.curve(circle1, circle2Control1, circle2Control2, circle2).constant(circle2), Paths.curve(circle2, circle3Control1, circle3Control2, circle3).constant(circle3), Paths.curve(circle3, circle4Control1, circle4Control2, circle4).constant(circle4));
    }

    public Path path3() {
        return Paths.path(Paths.line(circle4, star1).linear(circle4, star1), Paths.line(star1, star2).linear(star1, star2), Paths.line(star2, star3).linear(star2, star3), Paths.line(star3, star4).linear(star3, star4), Paths.line(star4, star5).linear(star4, star5), Paths.line(star5, star6).linear(star5, star6), Paths.line(star6, star7).linear(star6, star7), Paths.line(star7, star8).linear(star7, star8));
    }

    public Path reset() {
        return Paths.line(star8, reset).linear(star8, reset);
    }
}

