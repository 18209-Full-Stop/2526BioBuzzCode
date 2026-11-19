//package org.firstinspires.ftc.teamcode.robotCode.autos.practice;
//
//import com.pedropathing.api.Paths;
//
//import com.pedropathing.api.PoseFactory;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.math.Pose;
//import com.pedropathing.paths.Path;
//import com.pedropathing.ivy.Command;
//import com.pedropathing.ivy.Scheduler;
//import static com.pedropathing.ivy.Scheduler.schedule;
//import static com.pedropathing.ivy.groups.Groups.sequential;
//import static com.pedropathing.ivy.pedro.PedroCommands.follow;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.pedro.Constants;
//
//public class auto1 {
//
//    @Autonomous(name = "AutoPath", group = "Autonomous")
//    public class AutoPath extends LinearOpMode {
//
//        private Follower follower;
//
//        private final PoseFactory poseFactory = PoseFactory.degrees();
//
//        private final Pose start = poseFactory.of(4, 108, 90);
//        private final Pose path = poseFactory.of(132, 108, 0);
//        private final Pose point2 = poseFactory.of(132, 36, -90);
//        private final Pose point3 = poseFactory.of(36, 36, 180);
//        private final Pose point4 = poseFactory.of(36, 132, 90);
//        private final Pose point5 = poseFactory.of(108, 132, 0);
//        private final Pose point6 = poseFactory.of(108, 12, -90);
//        private final Pose point7 = poseFactory.of(12, 12, 180);
//        private final Pose point8 = poseFactory.of(12, 108, 90);
//
//        // Autonomous routine
//        public Command autoRoutine() {
//            return sequential(
//                    follow(follower, path()),
//                    follow(follower, path2()),
//                    follow(follower, path3()),
//                    follow(follower, path4()),
//                    follow(follower, path5()),
//                    follow(follower, path6()),
//                    follow(follower, path7()),
//                    follow(follower, path8())
//            );
//        }
//
//        @Override
//        public void runOpMode() {
//            Scheduler.reset();
//            follower = Constants.create(hardwareMap);
//            follower.setPose(start);
//            follower.update();
//
//            waitForStart();
//            schedule(autoRoutine());
//
//            while (opModeIsActive()) {
//                follower.update();
//                Scheduler.execute();
//
//                telemetry.addData("x", follower.pose().x());
//                telemetry.addData("y", follower.pose().y());
//                telemetry.addData("heading", follower.pose().heading());
//
//                if (follower.currentPath() != null) {
//                    telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
//                    telemetry.addData("Path number", follower.pathIndex());
//                }
//
//                telemetry.update();
//            }
//        }
//
//        public Path path() {
//            return Paths.line(start, path).constant(path);
//        }
//
//        public Path path2() {
//            return Paths.line(path, point2).tangent();
//        }
//
//        public Path path3() {
//            return Paths.line(point2, point3).tangent();
//        }
//
//        public Path path4() {
//            return Paths.line(point3, point4).tangent();
//        }
//
//        public Path path5() {
//            return Paths.line(point4, point5).tangent();
//        }
//
//        public Path path6() {
//            return Paths.line(point5, point6).tangent();
//        }
//
//        public Path path7() {
//            return Paths.line(point6, point7).tangent();
//        }
//
//        public Path path8() {
//            return Paths.line(point7, point8).tangent();
//        }
//    }
//
//}
