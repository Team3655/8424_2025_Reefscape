// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ArmConstants;
import frc.robot.subsystems.CANArmSubsystem;
import frc.robot.subsystems.CANDriveSubsystem;
import frc.robot.subsystems.CANWristSubsystem;
import frc.robot.subsystems.WristConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public final class Autos {

  // THESE ARE YOUR AUTO CHOICES

  public static Command exampleAuto(CANDriveSubsystem subsystem, 
    CANWristSubsystem wrist, 
    double distance, 
    double angle, 
    double maxSpeed,
    double wristSetpoint) {
      return Commands.sequence(
          new DriveDistanceCommand(subsystem, 0, 0),
          new DriveRotateCommand(subsystem, 0, 0),
          new WristCommand(wrist, wristSetpoint)
          );
  }

  public static Command exampleParallelCommand(CANDriveSubsystem subsystem) {
    return Commands.parallel(
      new DriveDistanceCommand(subsystem, 0, 0),
      new DriveRotateCommand(subsystem, 0, 0)
    );
  }

  public static Command exampleComboCommand(CANDriveSubsystem drive, CANWristSubsystem wrist){
    return Commands.parallel(
      new DriveDistanceCommand(drive, 0, 0),
      Commands.sequence(
        Commands.runOnce(()-> wrist.updateWristSetpoint(WristConstants.WRIST_LEVEL_START), wrist)));
  }

  public static Command testAuto(
    CANDriveSubsystem drive, 
    CANArmSubsystem arm, 
    CANWristSubsystem wristSubsystem) {

      return Commands.sequence(
        new DriveDistanceCommand(drive, 1, 0.5),
        new ArmCommand(arm, ArmConstants.ARM_LEVEL_2),
        new WristCommand(wristSubsystem, WristConstants.TRANSITION_STATE),
        new WaitCommand(1),
        new WristCommand(wristSubsystem, WristConstants.WRIST_LEVEL_release),
        new WaitCommand(1)

      );
    }

    public static Command crossLine(CANDriveSubsystem drive) {
      return new DriveDistanceCommand(drive, 1, 0.5);
    }

    public static Command forwardAndScore(CANDriveSubsystem drive, CANArmSubsystem arm, CANWristSubsystem wrist){
      return Commands.sequence(null);
    }

}
