// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cscore.CvSource;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
//import frc.robot.subsystems.AlgaeConstants;
import frc.robot.subsystems.ArmConstants;
//import frc.robot.subsystems.CANAlgaeSubsystem;
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
  //private final C algaeSubsystem;

  // The driver's controller
  private final CommandJoystick driverJoystick1 = new CommandJoystick(OperatorConstants.DRIVER_CONTROLLER_PORT);
  private final CommandJoystick driverJoystick2 = new CommandJoystick(1);
  private final CommandXboxController programmingController = new CommandXboxController(5);


  

  //UsbCamera usbCamera = new UsbCamera("USB Camera 0", 0);
//MjpegServer mjpegServer1 = new MjpegServer("USB Camera", 1181);

  // The operator's controller
  private final CommandGenericHID tractorController = new CommandGenericHID(2);
  private final CommandGenericHID RBController = new CommandGenericHID(4);

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
    //mjpegServer1.setSource(usbCamera);
    configureBindings();{
    //algaeSubsystem = new CANAlgaeSubsystem();
    autoChooser.addOption("testAuto", Autos.MIDDLE(driveSubsystem, armSubsystem, wristSubsystem));
    autoChooser.addOption("Cross the Line", Autos.crossLine(driveSubsystem));
    autoChooser.addOption("MIDDLE", Autos.MIDDLE(driveSubsystem, armSubsystem, wristSubsystem));
    autoChooser.addOption("Drive Distance", Autos.driveDistance(driveSubsystem, -0.5, 0.4));
    autoChooser.addOption("RIGHT", Autos.RIGHT(driveSubsystem, armSubsystem, wristSubsystem));
    SmartDashboard.putData("Auto Choices", autoChooser);}
      }
   public void Robot() {
    //m_visionThread =
        new Thread(
            () -> {
              // Get the UsbCamera from CameraServer
              UsbCamera camera = CameraServer.startAutomaticCapture();
              // Set the resolution
              camera.setResolution(640, 480);

              // Get a CvSink. This will capture Mats from the camera
              CvSink cvSink = CameraServer.getVideo();
              // Setup a CvSource. This will send images back to the Dashboard
              CvSource outputStream = CameraServer.putVideo("Rectangle", 640, 480);

              // Mats are very memory expensive. Lets reuse this Mat.
              Mat mat = new Mat();

              // This cannot be 'true'. The program will never exit if it is. This
              // lets the robot stop this thread when restarting robot code or
              // deploying.
              while (!Thread.interrupted()) {
                // Tell the CvSink to grab a frame from the camera and put it
                // in the source mat.  If there is an error notify the output.
                if (cvSink.grabFrame(mat) == 0) {
                  // Send the output the error.
                  outputStream.notifyError(cvSink.getError());
                  // skip the rest of the current iteration
                  continue;
                }
                // Put a rectangle on the image
                Imgproc.rectangle(
                    mat, new Point(100, 100), new Point(400, 400), new Scalar(255, 255, 255), 5);
                // Give the output stream a new image to display
                outputStream.putFrame(mat);
              }
            });
          
        
      


    // Set the options to show up in the Dashboard for selecting auto modes. If you
    // add additional auto modes you can add additional lines here with
    // autoChooser.addOption

    
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
         Commands.run(
           ()-> driveSubsystem.arcadeDrive(
             driverJoystick1.getRawAxis(1) * -0.6, 
             driverJoystick2.getRawAxis(0) * -0.6),
       driveSubsystem));

      
       
      //tractorController.button(18).onTrue(Commands.runOnce(() -> algaeSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem) );
  
       /*  driveSubsystem.setDefaultCommand(
          Commands.run(
            ()-> driveSubsystem.arcadeDrive(
            -1 * programmingController.getLeftY() * 0.5, 
            -1 * programmingController.getLeftX() * 0.5),
          driveSubsystem));*/

        programmingController.start().onTrue(Commands.runOnce(() -> driveSubsystem.resetEncoders(), driveSubsystem));




tractorController.button(1).onTrue(Commands.sequence(
          Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_FEEDER), armSubsystem),
          Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.WRIST_LEVEL_FEEDER), wristSubsystem)
        ));

                  tractorController.button(15).onTrue(
                    Commands.sequence(
                      Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_FIX), armSubsystem),
                      Commands.waitSeconds(1),
                      Commands.run(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem)
                    )
                    );

                    tractorController.button(10).onTrue(Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem) );
                   

                    tractorController.button(13).onTrue(
                      Commands.sequence(
                        Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_3), armSubsystem),
                        Commands.waitSeconds(1),
                        Commands.run(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem)
                      )
                      );
    

                      tractorController.button(11).onTrue(
      Commands.sequence(
        Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_FEEDER), armSubsystem),
        Commands.waitSeconds(.75),
        Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem),
        Commands.waitSeconds(.75),
        Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_2), armSubsystem)
      )
    );

 climberSubsystem.setDefaultCommand(climberSubsystem.manualClimber(() -> tractorController.getRawAxis(1), climberSubsystem));

    tractorController.button(6).onTrue(Commands.runOnce(()-> wristSubsystem.updateWristSetpoint(WristConstants.WRIST_LEVEL_START), wristSubsystem));
    tractorController.button(2).onTrue(Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_START), armSubsystem));
    //tractorController.button(1).onTrue(Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_FEEDER), armSubsystem));
    tractorController.button(5).onTrue(Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.WRIST_LEVEL_release), wristSubsystem));
    tractorController.button(7).onTrue(Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_FIX), armSubsystem));
   
    //tractorController.button(17).onTrue(Commands.runOnce(() -> algaeSubsystem.updateAlgaeSetpoint(AlgaeConstants.ALGAE_START), algaeSubsystem));
    //tractorController.button(19).onTrue(Commands.runOnce(() -> algaeSubsystem.updateAlgaeSetpoint(AlgaeConstants.ALGAE_OUT), algaeSubsystem));
   
   
   
    programmingController.b().onTrue(Commands.runOnce(()-> wristSubsystem.updateWristSetpoint(WristConstants.WRIST_LEVEL_START), wristSubsystem));
    programmingController.x().onTrue(Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_FIX), armSubsystem));
    programmingController.y().onTrue(Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_START), armSubsystem));
    programmingController.povLeft().onTrue(Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem) );
    programmingController.leftBumper().onTrue(Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_FEEDER), armSubsystem));
    programmingController.rightBumper().onTrue(Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.WRIST_LEVEL_release), wristSubsystem));

    programmingController.povRight().onTrue(
    Commands.sequence(
      Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_3), armSubsystem),
      Commands.waitSeconds(2),
      Commands.run(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem)
    )
    );

    programmingController.povDown().onTrue(
      Commands.sequence(
        Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_3), armSubsystem),
        Commands.waitSeconds(2),
        Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem),
        Commands.waitSeconds(2),
        Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_2), armSubsystem)
      )
    );


    programmingController.povUp().onTrue(
      Commands.sequence(
        Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_FIX), armSubsystem),
        Commands.waitSeconds(2),
        Commands.run(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem)
      )
      );





// New controler starts here




    climberSubsystem.setDefaultCommand(climberSubsystem.manualClimber(() -> RBController.getRawAxis(1), climberSubsystem));


    RBController.button(1).onTrue(Commands.sequence(
      Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_FEEDER), armSubsystem),
      Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.WRIST_LEVEL_FEEDER), wristSubsystem)
    ));

              RBController.button(4).onTrue(
                Commands.sequence(
                  Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_FIX), armSubsystem),
                  Commands.waitSeconds(1),
                  Commands.run(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem)
                )
                );

                

              RBController.button(3).onTrue(
                  Commands.sequence(
                    Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_3), armSubsystem),
                    Commands.waitSeconds(1),
                    Commands.run(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem)
                  )
                  );


                  RBController.button(2).onTrue(
  Commands.sequence(
    Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_FEEDER), armSubsystem),
    Commands.waitSeconds(.75),
    Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem),
    Commands.waitSeconds(.75),
    Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_LEVEL_2), armSubsystem)
  )
);



RBController.button(5).onTrue(Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_START), armSubsystem));
RBController.button(8).onTrue(Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.WRIST_LEVEL_release), wristSubsystem));
RBController.button(6).onTrue(Commands.runOnce(() -> armSubsystem.updateArmSetpoint(ArmConstants.ARM_FIX), armSubsystem));
RBController.button(7).onTrue(Commands.runOnce(() -> wristSubsystem.updateWristSetpoint(WristConstants.TRANSITION_STATE), wristSubsystem) );
               


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
