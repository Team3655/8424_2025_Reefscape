// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class DriveDistanceCommand extends Command {
  public CANDriveSubsystem m_drive;
  public double m_distance;
  public double m_speed;

  /** Creates a new DriveDistanceCommand. */
  public DriveDistanceCommand(CANDriveSubsystem drive, double distance, double speed) {
    m_drive = drive;
    m_distance = distance;
    m_speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.driveArcade(m_drive, () -> m_speed, () -> 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.driveArcade(m_drive, () -> 0.0, () -> 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(m_drive.getDriveInches()) > m_distance) {
      return true;
    }
    return false;
  }
}
