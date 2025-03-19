// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANWristSubsystem extends SubsystemBase {

  public final SparkMax Wrist;

  private final SparkMaxConfig WristConfig;
  private SparkClosedLoopController pidController;

  private double setpoint;

  /** Creates a new ArmSubsystem. */
  public CANWristSubsystem() {
    WristConfig = new SparkMaxConfig();

    WristConfig.closedLoop.p(3);
    WristConfig.closedLoop.i(0);
    WristConfig.closedLoop.d(0);

    WristConfig.inverted(true);

    WristConfig.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder);
    WristConfig.absoluteEncoder.inverted(true);
    WristConfig.absoluteEncoder.zeroCentered(true);
    WristConfig.closedLoopRampRate(3);

    WristConfig.softLimit.forwardSoftLimit(0.26);
    WristConfig.softLimit.reverseSoftLimit(-0.26);
    WristConfig.softLimit.forwardSoftLimitEnabled(true);
    WristConfig.softLimit.reverseSoftLimitEnabled(true);

    Wrist = new SparkMax(5, MotorType.kBrushless);
    Wrist.configure(WristConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    pidController = Wrist.getClosedLoopController();

    setpoint = WristConstants.WRIST_LEVEL_START;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Wrist Postion", Wrist.getAbsoluteEncoder().getPosition());
    SmartDashboard.putNumber("Wrist Setpoint DEG", Units.rotationsToDegrees(setpoint));

    pidController.setReference(setpoint, ControlType.kPosition);
  }

  public void resetEncoders() {
    Wrist.getEncoder().setPosition(0);
  }

  public void updateWristSetpoint(double setpoint) {
    this.setpoint = setpoint;
  }
  // public Command manualWrist(DoubleSupplier speed, CANWristSubsystem wristSubsystem) {
  //   return Commands.run(() -> Wrist.set(speed.getAsDouble()), wristSubsystem);
  // }

 



}