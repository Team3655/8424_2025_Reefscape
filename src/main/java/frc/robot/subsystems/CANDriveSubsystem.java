// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANDriveSubsystem extends SubsystemBase {
  private final SparkMax leftFront;
  private final SparkMax leftBack;
  private final SparkMax rightFront;
  private final SparkMax rightBack;

  private final DifferentialDrive drive;

  private final AHRS gyro;

  private final double DRIVE_GEAR_REDUCTION = 10.75;
  private final double WHEEL_RADIUS_METERS = Units.inchesToMeters(3);
  public final double WHEEL_CIRCUMFERENCE_METERS = 2 * Math.PI * WHEEL_RADIUS_METERS;

  private double xSpeed;
  private double zRotation;

  public CANDriveSubsystem() {

    xSpeed = 0.0;
    zRotation = 0.0;
    // create BRUSHLESS motors for drive and arm

    leftFront = new SparkMax(1, MotorType.kBrushless);
    leftBack = new SparkMax(2, MotorType.kBrushless);
    rightFront = new SparkMax(3, MotorType.kBrushless);
    rightBack = new SparkMax(4, MotorType.kBrushless);

    // set up differential drive class
    drive = new DifferentialDrive(leftFront, rightFront);

    // Set up gyro
    gyro = new AHRS(NavXComType.kMXP_SPI);

    // Set can timeout. Because this project only sets parameters once on
    // construction, the timeout can be long without blocking robot operation. Code
    // which sets or gets parameters during operation may need a shorter timeout.
    leftFront.setCANTimeout(250);
    leftBack.setCANTimeout(250);
    rightFront.setCANTimeout(250);
    rightBack.setCANTimeout(250);

    // Create the configuration to apply to motors. Voltage compensation
    // helps the robot perform more similarly on different
    // battery voltages (at the cost of a little bit of top speed on a fully charged
    // battery). The current limit helps prevent tripping
    // breakers.
    SparkMaxConfig config = new SparkMaxConfig();
    config.voltageCompensation(12);
    config.encoder.positionConversionFactor(1);

    // Set configuration to follow leader and then apply it to corresponding
    // follower. Resetting in case a new controller is swapped
    // in and persisting in case of a controller reset due to breaker trip
    config.follow(leftFront);
    leftBack.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    config.follow(rightFront);
    rightBack.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Remove following, then apply config to right leader
    config.disableFollowerMode();
    config.inverted(true);
    rightFront.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    // Set conifg to inverted and then apply to left leader. Set Left side inverted
    // so that postive values drive both sides forward
    config.inverted(false);
    leftFront.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Drive Meters", getDriveMeters());
    SmartDashboard.putNumber("Drive Inches", Units.metersToInches(getDriveMeters()));
    SmartDashboard.putNumber("Gyro Deg", getGyroYaw());

    drive.arcadeDrive(xSpeed, zRotation);
  }

  // Command to drive the robot with joystick inputs
  // public Command driveArcade(CANDriveSubsystem driveSubsystem, DoubleSupplier xSpeed, DoubleSupplier zRotation) {
  //   return Commands.run(
  //       () -> drive.arcadeDrive(xSpeed.getAsDouble(), zRotation.getAsDouble()), driveSubsystem);
  // }

  public void arcadeDrive(double xSpeed, double zRotation) {
    //drive.arcadeDrive(xSpeed, zRotation);

    this.xSpeed = xSpeed;
    this.zRotation = zRotation;
  }

  // public void stop() {
  //   leftFront.set(0);
  //   rightFront.set(0);
  // }

  public void resetEncoders() {
    leftFront.getEncoder().setPosition(0);
    leftBack.getEncoder().setPosition(0);
    rightFront.getEncoder().setPosition(0);
    rightBack.getEncoder().setPosition(0);
  }

  public double getDriveMeters() {
    double distanceMeters = (leftFront.getEncoder().getPosition() / DRIVE_GEAR_REDUCTION) * WHEEL_CIRCUMFERENCE_METERS;
    return distanceMeters;
  }


  public void resetGyro() {
    gyro.reset();
  }

  public double getGyroYaw(){
    return -1 * gyro.getYaw();
  }
}
