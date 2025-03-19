// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANArmSubsystem extends SubsystemBase {

  public final SparkMax Arm;

  private final SparkMaxConfig ArmConfig;

  private final SparkClosedLoopController sparkController;

  private double setpoint;

  AnalogInput coralSensor = new AnalogInput(3);

  /** Creates a new ArmSubsystem. */
  public CANArmSubsystem() {

    ArmConfig = new SparkMaxConfig();

    ArmConfig.idleMode(IdleMode.kCoast);

    ArmConfig.closedLoop.p(.06);
    ArmConfig.closedLoop.i(0);
    ArmConfig.closedLoop.d(0);

    ArmConfig.limitSwitch.forwardLimitSwitchType(Type.kNormallyOpen);
    ArmConfig.limitSwitch.forwardLimitSwitchEnabled(true);
    ArmConfig.idleMode(IdleMode.kBrake);
 
    Arm = new SparkMax(32, MotorType.kBrushless);

    Arm.configure(ArmConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    sparkController = Arm.getClosedLoopController();

    Arm.getEncoder().setPosition(0.0);
    setpoint = ArmConstants.ARM_START;

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Arm Postion", Arm.getEncoder().getPosition());
    SmartDashboard.putNumber("Coral Sensor", getCoralSensor());
    sparkController.setReference(setpoint, ControlType.kPosition);
  }

  public void resetEncoders() {

    // Arm.getEncoder().setPosition(0);
  }

  public void updateArmSetpoint(double setpoint) {
    this.setpoint = setpoint;
  }

  public double getCoralSensor() {
    return coralSensor.getValue();
  }

  // public Command manualArm(DoubleSupplier speed, CANArmSubsystem armSubsystem) {
  //   return Commands.run(() -> Arm.set(speed.getAsDouble()), armSubsystem);
  // }

}