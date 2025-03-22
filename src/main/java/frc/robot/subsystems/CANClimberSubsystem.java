package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANClimberSubsystem extends SubsystemBase {
    
private final SparkMax Climber; 

  private final SparkMaxConfig ClimberConfig;

public CANClimberSubsystem() {

  Climber = new SparkMax(7, MotorType.kBrushless);

  ClimberConfig = new SparkMaxConfig();

  ClimberConfig.idleMode(IdleMode.kBrake);

 Climber.configure(ClimberConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

}

 @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Climber", Climber.getEncoder().getPosition());
  }

public Command manualClimber(DoubleSupplier voltage, CANClimberSubsystem climberSubsystem) { 
     return Commands.run(() -> Climber.setVoltage(voltage.getAsDouble() * 12), climberSubsystem);
  }

}
