package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANClimberSubsystem extends SubsystemBase {
    
private final SparkMax Climber; 

private final SparkMaxConfig ClimberConfig; 


public CANClimberSubsystem() {

ClimberConfig = new SparkMaxConfig();

 Climber = new SparkMax(7, MotorType.kBrushless);
}

}
