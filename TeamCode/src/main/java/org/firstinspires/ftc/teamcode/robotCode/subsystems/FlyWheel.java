package org.firstinspires.ftc.teamcode.robotCode.subsystems;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class FlyWheel {
    //Motors
    private DcMotor flyWheelPol;
    private DcMotor flyWheelNec;

    //Servos
    private Servo polGate;
    private Servo necGate;

    //Timers, variables
    public ElapsedTime runtime = new ElapsedTime();
    private static final double gatePosDown = 0.5;
    private static final double gatePosUp = 0.2;

    public FlyWheel(HardwareMap hardwareMap) {
        flyWheelPol = hardwareMap.get(DcMotor.class, "flyWheelPol");
        flyWheelPol.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        flyWheelNec = hardwareMap.get(DcMotor.class, "flyWheelNec");
        flyWheelNec.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        polGate = hardwareMap.get(Servo.class, "polGate");
        necGate = hardwareMap.get(Servo.class, "necGate");
    }

    public Command flyWheelPolSetPower(double power) {
        return Command.build()
                .setExecute(() -> {
                    flyWheelPol.setPower(power);
                })
                .setEnd(endCondition -> flyWheelPol.setPower(0));
    }

    public Command flyWheelNecSetPower(double power) {
        return Command.build()
                .setExecute(() -> {
                    flyWheelNec.setPower(power);
                })
                .setEnd(endCondition -> flyWheelNec.setPower(0));
    }

    public Command launch(boolean useBoth){
        return Command.build()
                .setStart(() -> {
                    runtime.reset();
                    polGate.setPosition(gatePosDown);
                    necGate.setPosition(gatePosDown);
                })
                .setExecute(() -> {
                    flyWheelPol.setPower(1.0);
                    polGate.setPosition(gatePosUp);
                    if (useBoth){
                        flyWheelNec.setPower(1.0);
                        necGate.setPosition(gatePosUp);
                    }
                })
                .setDone(() -> runtime.seconds() >= 1.5)
                .setEnd(endCondition -> {
                    flyWheelPol.setPower(0);
                    flyWheelNec.setPower(0);
                    polGate.setPosition(gatePosDown);
                    necGate.setPosition(gatePosDown);
                });
    }

    public void setPowerNec(double power){
        flyWheelNec.setPower(power);
    }

    public void setPowerPol(double power){
        flyWheelPol.setPower(power);
    }

    public void closePol(){
        polGate.setPosition(gatePosDown);
    }

    public void openPol(){
        polGate.setPosition(gatePosUp);
    }

    public void closeNec(){
        necGate.setPosition(gatePosDown);
    }

    public void openNec(){
        necGate.setPosition(gatePosUp);
    }
}
