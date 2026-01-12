// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.drive;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

import static frc.robot.Constants.DriveConstants.*;

public class DriveVictor implements DriveIO {
    private final VictorSP leftLeader;
    private final VictorSP leftFollower;
    private final VictorSP rightLeader;
    private final VictorSP rightFollower;

    public DriveVictor() {
        // Create motor controllers (VictorSPX driving CIMs)
        leftLeader = new VictorSP(LEFT_LEADER_ID);
        leftFollower = new VictorSP(LEFT_FOLLOWER_ID);
        rightLeader = new VictorSP(RIGHT_LEADER_ID);
        rightFollower = new VictorSP(RIGHT_FOLLOWER_ID);

        // Followers follow their leaders
        leftLeader.addFollower(leftFollower);
        rightLeader.addFollower(rightFollower);

        // Inversion:
        // Typical convention: invert ONE side so + output drives both sides forward.
        // Match your old code: left side inverted true.
        leftLeader.setInverted(false);
        rightLeader.setInverted(true);

        // Make followers match their leader inversion
        leftFollower.setInverted(leftLeader.getInverted());
        rightFollower.setInverted(rightLeader.getInverted());
    }

    @Override
    public void updateInputs(DriveIOInputs inputs) {
        inputs.leftVolts = leftLeader.getVoltage();
        inputs.rightVolts = rightLeader.getVoltage();
    }

    @Override
    public void setOutputPercent(double left, double right) {
        leftLeader.set(left);
        rightLeader.set(right);

        leftLeader.feed();
        rightLeader.feed();
    }
}
