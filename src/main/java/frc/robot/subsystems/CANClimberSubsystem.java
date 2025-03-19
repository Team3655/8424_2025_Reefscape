package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANClimberSubsystem extends SubsystemBase {
    
// private final SparkMax Climber; 

private final SparkMaxConfig ClimberConfig; 


public CANClimberSubsystem() {

    ClimberConfig = new SparkMaxConfig();

//  Climber = new SparkMax(7, MotorType.kBrushless);

// Climber.configure(ClimberConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

}

//public Command manualClimber(DoubleSupplier voltage, CANClimberSubsystem climberSubsystem) { 
//    return Commands.run(() -> Climber.setVoltage(voltage.getAsDouble() * 12), climberSubsystem);
  }

//}
