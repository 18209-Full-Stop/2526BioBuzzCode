package org.firstinspires.ftc.teamcode.robotCode.subsystems;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class FlyWheel {
    private DcMotor flyWheelPol;
    private DcMotor flyWheelNec;
    public ElapsedTime runtime = new ElapsedTime();

    public FlyWheel(HardwareMap hardwareMap) {
        flyWheelPol = hardwareMap.get(DcMotor.class, "flyWheelPol");
        flyWheelPol.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        flyWheelNec = hardwareMap.get(DcMotor.class, "flyWheelNec");
        flyWheelNec.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
                })
                .setExecute(() -> {
                    flyWheelPol.setPower(1.0);
                    if (useBoth){
                        flyWheelNec.setPower(1.0);
                    }
                })
                .setDone(() -> runtime.seconds() >= 1.5)
                .setEnd(endCondition -> {
                    flyWheelPol.setPower(0);
                    flyWheelNec.setPower(0);
                });
    }

    public void setPowerNec(double power){
        flyWheelNec.setPower(power);
    }

    public void setPowerPol(double power){
        flyWheelPol.setPower(power);
    }
}
