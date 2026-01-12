// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import static frc.robot.Constants.DriveConstants.*;

public class DriveVictor implements DriveIO {
    private final WPI_VictorSPX leftLeader;
    private final WPI_VictorSPX leftFollower;
    private final WPI_VictorSPX rightLeader;
    private final WPI_VictorSPX rightFollower;

    public DriveVictor() {
        // Create motor controllers (VictorSPX driving CIMs)
        leftLeader = new WPI_VictorSPX(LEFT_LEADER_ID);
        leftFollower = new WPI_VictorSPX(LEFT_FOLLOWER_ID);
        rightLeader = new WPI_VictorSPX(RIGHT_LEADER_ID);
        rightFollower = new WPI_VictorSPX(RIGHT_FOLLOWER_ID);

        // (Optional) Factory default to clear any old config on used controllers
        leftLeader.configFactoryDefault();
        leftFollower.configFactoryDefault();
        rightLeader.configFactoryDefault();
        rightFollower.configFactoryDefault();

        // Set neutral mode (Brake helps stop; Coast feels smoother)
        leftLeader.setNeutralMode(NeutralMode.Brake);
        leftFollower.setNeutralMode(NeutralMode.Brake);
        rightLeader.setNeutralMode(NeutralMode.Brake);
        rightFollower.setNeutralMode(NeutralMode.Brake);

        // Followers follow their leaders
        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        // Inversion:
        // Typical convention: invert ONE side so + output drives both sides forward.
        // Match your old code: left side inverted true.
        leftLeader.setInverted(true);
        rightLeader.setInverted(false);

        // Make followers match their leader inversion
        leftFollower.setInverted(InvertType.FollowMaster);
        rightFollower.setInverted(InvertType.FollowMaster);
    }

    @Override
    public void updateInputs(DriveIOInputs inputs) {
        inputs.leftVolts = leftLeader.getMotorOutputVoltage();
        inputs.rightVolts = rightLeader.getMotorOutputVoltage();
    }

    @Override
    public void setOutputPercent(double left, double right) {
        leftLeader.set(left);
        rightLeader.set(right);
    }
}
