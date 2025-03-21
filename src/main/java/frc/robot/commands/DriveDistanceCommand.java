// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class DriveDistanceCommand extends Command {
  public CANDriveSubsystem m_drive;
  public double m_distanceMeters;
  public double m_maxSpeed;

  private PIDController controller;

  /** Creates a new DriveDistanceCommand. */
  public DriveDistanceCommand(CANDriveSubsystem drive, double distanceMeters, double maxSpeed) {
    m_drive = drive;
    m_distanceMeters = distanceMeters;
    m_maxSpeed = maxSpeed;

    controller = new PIDController(7, 0, 0);
    controller.setTolerance(0.02);

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
    double speed = controller.calculate(m_drive.getDriveMeters(), m_distanceMeters);
    double clampedSpeed = MathUtil.clamp(speed, -m_maxSpeed, m_maxSpeed);
    m_drive.arcadeDrive(clampedSpeed, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // if (Math.abs(m_drive.getDriveMeters()) > m_distanceMeters) {
    //   return true;
    // }
    if(controller.atSetpoint()) {
      return true;
    }

    return false;
  }
}
