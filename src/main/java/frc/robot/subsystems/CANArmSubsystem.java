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

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANArmSubsystem extends SubsystemBase {

  public final SparkMax Arm;
  public final SparkMax algae;

  private final SparkMaxConfig ArmConfig;
  private final SparkMaxConfig algaeConfig;

  private final SparkClosedLoopController sparkController;

  private double setpoint;

  /** Creates a new ArmSubsystem. */
  public CANArmSubsystem() {

    ArmConfig = new SparkMaxConfig();
    algaeConfig = new SparkMaxConfig();

    ArmConfig.closedLoop.p(.1);
    ArmConfig.closedLoop.i(0);
    ArmConfig.closedLoop.d(0);

    Arm = new SparkMax(32, MotorType.kBrushless);
    algae = new SparkMax(8, MotorType.kBrushless);

    Arm.configure(ArmConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    algae.configure(ArmConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    sparkController = Arm.getClosedLoopController();

    setpoint = 0.0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Arm Postion", Arm.getEncoder().getPosition());
    //sparkController.setReference(setpoint, ControlType.kPosition);
  }

  public void resetEncoders() {

    // Arm.getEncoder().setPosition(0);
  }

  public void setArmSetpoint(double setpoint) {
    this.setpoint = setpoint;
  }

  public Command manualArm(DoubleSupplier voltage, CANArmSubsystem armSubsystem) { 
    return Commands.run(() -> Arm.setVoltage(voltage.getAsDouble() * 12), armSubsystem);
  }

}