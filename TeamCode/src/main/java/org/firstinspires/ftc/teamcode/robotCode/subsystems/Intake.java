package org.firstinspires.ftc.teamcode.robotCode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.util.ElapsedTime;

import static com.pedropathing.ivy.Scheduler.schedule;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class Intake {
    private DcMotor intake;
    private Servo necGate;
    private Servo polGate;

    //Variables
    final double gatePosDownNec = 0;
    final double gatePosUpNec = 0.2;
    final double gatePosDownPol = 1;
    final double gatePosUpPol = 0.5;
    public ElapsedTime runtime = new ElapsedTime();

    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotor.class, "intake");
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        necGate = hardwareMap.get(Servo.class, "necGate");
        polGate = hardwareMap.get(Servo.class, "polGate");
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

    public void necGateOpen() {
        necGate.setPosition(gatePosUpNec);
    }

    public void necGateClose() {
        necGate.setPosition(gatePosDownNec);
    }

    public void polGateOpen() {
        polGate.setPosition(gatePosUpPol);
    }

    public void polGateClose() {
        polGate.setPosition(gatePosDownPol);
    }

    public void setPower(double power){
        intake.setPower(power);
    }


    public void gateMove(double pos) {
//        gate.setPosition(pos);
    }

    public void setNecPos(double pos){
        necGate.setPosition(pos);
    }

    public void setPolPos(double pos){
        polGate.setPosition(pos);
    }

    public Command refill(){
       return Command.build()
               .setStart(() -> {
                   necGateOpen();
                   runtime.reset();
               })
                .setExecute(() -> {
                    intake.setPower(1.0);
                })
                .setDone(() -> runtime.seconds() >= 1.5)
                .setEnd(endCondition -> {
                    intake.setPower(0);
                    necGateClose();
                });
    }
}
