package org.firstinspires.ftc.teamcode.robotCode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.util.ElapsedTime;

import static com.pedropathing.ivy.Scheduler.schedule;

public class Intake {
    private DcMotor intake;
    private Servo gate;

    //Variables
    final double gatePosDown = 0.5;
    final double gatePosUp = 0.2;
    public ElapsedTime runtime = new ElapsedTime();

    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotor.class, "intake");
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        gate = hardwareMap.get(Servo.class, "gate");
    }

    public Command intakeSetPower(double power) {
        return Command.build()
                .setExecute(() -> {
                    intake.setPower(power);
                })
                .setEnd(endCondition -> intake.setPower(0));
    }

    public Command intakeSetPowerFor(double power, double duration) {
        return Command.build()
                .setStart(() -> runtime.reset())
                .setExecute(() -> intake.setPower(power))
                .setDone(() -> runtime.seconds() >= duration)
                .setEnd(endCondition -> intake.setPower(0));
    }

    public void gateOpen() {
        gate.setPosition(gatePosUp);
    }

    public void setPower(double power){
        intake.setPower(power);
    }

    public void gateClose() {
        gate.setPosition(gatePosDown);
    }

    public void gateMove(double pos) {
        gate.setPosition(pos);
    }

    public Command refill(){
       return Command.build()
               .setStart(() -> {
                   gateOpen();
                   runtime.reset();
               })
                .setExecute(() -> {
                    intake.setPower(1.0);
                })
                .setDone(() -> runtime.seconds() >= 1.5)
                .setEnd(endCondition -> {
                    intake.setPower(0);
                    gateClose();
                });
    }
}
