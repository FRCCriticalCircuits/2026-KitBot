// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.DriveConstants.*;

public class CANDriveSubsystem extends SubsystemBase {
  private final WPI_VictorSPX leftLeader;
  private final WPI_VictorSPX leftFollower;
  private final WPI_VictorSPX rightLeader;
  private final WPI_VictorSPX rightFollower;

  private final DifferentialDrive drive;

  public CANDriveSubsystem() {
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

    // DifferentialDrive expects SpeedController / MotorController objects.
    drive = new DifferentialDrive(leftLeader, rightLeader);

    // Safety (optional but recommended)
    drive.setSafetyEnabled(true);
    drive.setDeadband(0.02);
  }

  @Override
  public void periodic() {
  }

  // Command factory to create command to drive the robot with joystick inputs.
  public Command driveArcade(DoubleSupplier xSpeed, DoubleSupplier zRotation) {
    return this.run(() -> drive.arcadeDrive(xSpeed.getAsDouble(), zRotation.getAsDouble()));
  }
}
