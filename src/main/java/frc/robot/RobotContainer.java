// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.subsystems.ArmConstants;
import frc.robot.subsystems.CANArmSubsystem;
import frc.robot.subsystems.CANClimberSubsystem;
import frc.robot.subsystems.CANDriveSubsystem;
import frc.robot.subsystems.CANWristSubsystem;
import frc.robot.subsystems.WristConstants;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final CANDriveSubsystem driveSubsystem;
  private final CANArmSubsystem armSubsystem;
  @SuppressWarnings("unused")
  private final CANClimberSubsystem climberSubsystem;
  private final CANWristSubsystem wristSubsystem;

  // The driver's controller
  private final CommandJoystick driverJoystick1 = new CommandJoystick(OperatorConstants.DRIVER_CONTROLLER_PORT);
  private final CommandJoystick driverJoystick2 = new CommandJoystick(1);
  private final CommandXboxController programmingController = new CommandXboxController(5);

  // The operator's controller
  private final CommandGenericHID tractorController = new CommandGenericHID(2);

  // The autonomous chooser
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    driveSubsystem = new CANDriveSubsystem();
    armSubsystem = new CANArmSubsystem();
    climberSubsystem = new CANClimberSubsystem();
    wristSubsystem = new CANWristSubsystem();

    configureBindings();

    // Set the options to show up in the Dashboard for selecting auto modes. If you
    // add additional auto modes you can add additional lines here with
    // autoChooser.addOption
    autoChooser.setDefaultOption("Autonomous", Autos.exampleAuto(driveSubsystem));
    autoChooser.addOption("DriveDistance", Autos.driveDistance(driveSubsystem, 1.5,  1.0));
  }  

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {

    driveSubsystem.setDefaultCommand(
        driveSubsystem.driveArcade(
            driveSubsystem, 
            () -> driverJoystick1.getRawAxis(1) * 0.5, 
            () -> driverJoystick2.getRawAxis(0) * 0.5));

           /* driveSubsystem.setDefaultCommand(
              driveSubsystem.driveArcade(
                  driveSubsystem, 
                  () -> programmingController.getLeftY() * 0.5, 
                  () -> programmingController.getLeftX() * 0.5));*/


                  tractorController.button(4).onTrue(
                    Commands.sequence(
                      Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_4), armSubsystem),
                      Commands.waitSeconds(2),
                      Commands.run(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem)
                    )
                    );


                    tractorController.button(3).onTrue(
                      Commands.sequence(
                        Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_3), armSubsystem),
                        Commands.waitSeconds(2),
                        Commands.run(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem)
                      )
                      );
    

                      tractorController.button(2).onTrue(
      Commands.sequence(
        Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_3), armSubsystem),
        Commands.waitSeconds(2),
        Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem),
        Commands.waitSeconds(2),
        Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_2), armSubsystem)
      )
    );


    tractorController.button(7).onTrue(Commands.runOnce(()-> wristSubsystem.updateWristSetpoint(WristConstants.WRIST_LEVEL_START), wristSubsystem));
    tractorController.button(6).onTrue(Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_START), armSubsystem));
    tractorController.button(1).onTrue(Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_FEEDER), armSubsystem));
    tractorController.button(5).onTrue(Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.WRIST_LEVEL_release), wristSubsystem));



    programmingController.a().onTrue(Commands.runOnce(()-> wristSubsystem.updateWristSetpoint(WristConstants.TEST), wristSubsystem));
    programmingController.b().onTrue(Commands.runOnce(()-> wristSubsystem.updateWristSetpoint(WristConstants.WRIST_LEVEL_START), wristSubsystem));
    
    programmingController.x().onTrue(Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_4), armSubsystem));
    programmingController.y().onTrue(Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_START), armSubsystem));

    programmingController.leftBumper().onTrue(Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_FEEDER), armSubsystem));
    programmingController.rightBumper().onTrue(Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.WRIST_LEVEL_release), wristSubsystem));


    programmingController.povLeft().onTrue(Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.WRIST_LET_GO), wristSubsystem));
    
    programmingController.povRight().onTrue(
    Commands.sequence(
      Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_4), armSubsystem),
      Commands.waitSeconds(2),
      Commands.run(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem)
    )
    );
    
    
    programmingController.povDown().onTrue(
    Commands.sequence(
      Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_3), armSubsystem),
      Commands.waitSeconds(2),
      Commands.run(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem)
    )
    );

    programmingController.povUp().onTrue(
      Commands.sequence(
        Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_3), armSubsystem),
        Commands.waitSeconds(2),
        Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem),
        Commands.waitSeconds(2),
        Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_2), armSubsystem)
      )
    );


    tractorController.button(21).onTrue(
      Commands.sequence(
        Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_3), armSubsystem),
        Commands.waitSeconds(2),
        Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem),
        Commands.waitSeconds(2),
        Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_2), armSubsystem)
      )
    );

    // Set the default command for the roller subsystem to the command from the
    // factory with the values provided by the triggers on the operator controller

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }
}
