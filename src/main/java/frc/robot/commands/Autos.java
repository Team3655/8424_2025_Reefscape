// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.CANDriveSubsystem;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static Command exampleAuto(CANDriveSubsystem subsystem) {
    return Commands.sequence(
        driveDistance(subsystem, 0, 0),
        rotateAngle(subsystem, 0, 0)
        );
  }

  public static Command driveDistance(CANDriveSubsystem subsystem, double distance, double speed) {
    return new DriveDistanceCommand(subsystem, distance, speed);
  }

  public static Command rotateAngle(CANDriveSubsystem subsystem, double angle, double speed) {
    return new DriveRotateCommand(subsystem, angle, speed);
  }
}
