package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANAlgaeSubsystem extends SubsystemBase {

  public final SparkMax AlgaeIntake;
  public final SparkMax Algae;

  private final SparkMaxConfig AlgaeConfig;

  private final SparkClosedLoopController sparkController;

  private double setpoint;

  /** Creates a new ArmSubsystem. */
  public CANAlgaeSubsystem() {

    AlgaeConfig = new SparkMaxConfig();

    AlgaeConfig.idleMode(IdleMode.kCoast);

    AlgaeConfig.closedLoop.p(.07);
    AlgaeConfig.closedLoop.i(0);
    AlgaeConfig.closedLoop.d(0);
 
    Algae = new SparkMax(40, MotorType.kBrushless);
    AlgaeIntake = new SparkMax(41, MotorType.kBrushless);

    Algae.configure(AlgaeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    sparkController = Algae.getClosedLoopController();

    Algae.getEncoder().setPosition(0.0);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Algae Postion", Algae.getEncoder().getPosition());
    sparkController.setReference(setpoint, ControlType.kPosition);
  }

  public void resetEncoders() {

    // Arm.getEncoder().setPosition(0);
  }

  public void updateAlgaeSetpoint(double setpoint) {
    this.setpoint = setpoint;
  }

public Command AlgaeIntake(DoubleSupplier voltage, CANAlgaeSubsystem algaeSubsystem) { 
     return Commands.run(() -> AlgaeIntake.setVoltage(voltage.getAsDouble() * 10), algaeSubsystem);
  }

}