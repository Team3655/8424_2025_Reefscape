// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkMaxAlternateEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANWristSubsystem extends SubsystemBase {

  public final SparkMax Wrist;

  private final SparkMaxConfig WristConfig;

  private SparkClosedLoopController pidController;

  private SparkMaxAlternateEncoder absoluteEncoder;

  private double setpoint;

  /** Creates a new ArmSubsystem. */
  public CANWristSubsystem() {
    WristConfig = new SparkMaxConfig();

    WristConfig.closedLoop.p(.05);
    WristConfig.closedLoop.i(0);
    WristConfig.closedLoop.d(0);

    WristConfig.closedLoopRampRate(3);

    Wrist = new SparkMax(5, MotorType.kBrushless);

    pidController = Wrist.getClosedLoopController();

    setpoint = WristConstants.WRIST_LEVEL_START;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Wrist Postion", Wrist.getEncoder().getPosition());

    pidController.setReference(setpoint, ControlType.kPosition);
  }

  public void resetEncoders() {
    Wrist.getEncoder().setPosition(0);
  }

  public void updateWristSetpoint(double setpoint) {
    this.setpoint = setpoint;
  }
  public Command manualWrist(DoubleSupplier speed, CANWristSubsystem wristSubsystem) {
    return Commands.run(() -> Wrist.set(speed.getAsDouble()), wristSubsystem);
  }

 



}