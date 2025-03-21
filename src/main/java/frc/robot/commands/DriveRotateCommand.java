// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class DriveRotateCommand extends Command {

  CANDriveSubsystem m_drive;
  double m_angleDegrees;
  double m_maxRotSpeed;
  private PIDController controller;

  /** Creates a new DriveRotateCommand. */
  public DriveRotateCommand(CANDriveSubsystem drive, double angleDegrees, double maxRotSpeed) {
    m_drive = drive;
    m_angleDegrees = angleDegrees;
    m_maxRotSpeed = maxRotSpeed;
    controller = new PIDController(0.009, 0, 0);
    controller.enableContinuousInput(-180, 180);
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
    double speed = controller.calculate(m_drive.getGyroYaw(), m_angleDegrees);
    speed = MathUtil.clamp(speed, -m_maxRotSpeed, m_maxRotSpeed);
    m_drive.arcadeDrive(0, speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(controller.atSetpoint()) {
      return true;
    }
    return false;
  }
}
