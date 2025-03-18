// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class DriveRotateCommand extends Command {

  CANDriveSubsystem m_drive;
  double m_angle;
  double m_rotationSpeed;

  /** Creates a new DriveRotateCommand. */
  public DriveRotateCommand(CANDriveSubsystem drive, double angle, double rotationSpeed) {
    m_drive = drive;
    m_angle = angle;
    m_rotationSpeed = rotationSpeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.resetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.driveArcade(m_drive, () -> 0.0, () -> m_rotationSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.driveArcade(m_drive, () -> 0.0, () -> 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(m_drive.getYaw() < m_angle + 5 && m_drive.getYaw() > m_angle - 5) return true;
    return false;
  }
}
