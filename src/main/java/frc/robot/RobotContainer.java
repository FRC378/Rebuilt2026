// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

//Autos
import frc.robot.commands.CmdAutoDoNothing;
import frc.robot.commands.CmdAutoCenter;
import frc.robot.commands.CmdAutoLeft;
import frc.robot.commands.CmdAutoRight;


//Commands
import frc.robot.commands.CmdPrintText;
import frc.robot.commands.CmdShooterActivate;
import frc.robot.commands.CmdShooterDefault;
import frc.robot.commands.CmdShooterSetPosition;
import frc.robot.commands.CmdShooterSetPosition.ShooterPositon;
import frc.robot.commands.CmdIntakeDeploy;
import frc.robot.commands.CmdIntakeRetract;
import frc.robot.commands.CmdDriveClearAll;
import frc.robot.commands.CmdDriveForcePark;
import frc.robot.commands.CmdDriveForceTurnAngle;
import frc.robot.commands.CmdDriveTypeToggle;
import frc.robot.commands.CmdDriveWithGamepad;
import frc.robot.commands.CmdDriveZeroGyro;
import frc.robot.commands.CmdShooterSetPower;


//Subsystems
import frc.robot.subsystems.Handler;  
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Drivetrain;



public class RobotContainer {

  //****************Controllers*******************
  public static final CommandXboxController m_driver = new CommandXboxController(0);
  public static final CommandXboxController m_controller = new CommandXboxController(1);

  //****************Subsystems*******************
  public static PowerDistribution m_pdp  = new PowerDistribution(Constants.PDP_CAN_ID, PowerDistribution.ModuleType.kCTRE);
  public static Intake m_intake = new Intake ();
  public static Handler m_handler = new Handler();
  public static Shooter m_Shooter = new Shooter();
  public static Drivetrain  m_drivetrain = new Drivetrain();


  //****************Auto Chooser*******************
  private final SendableChooser<Command> m_autoChooser = new SendableChooser<>();


  public RobotContainer() {

    //****************Default Commands**************
    m_Shooter.setDefaultCommand(new CmdShooterDefault());
    m_drivetrain.setDefaultCommand( new CmdDriveWithGamepad() );

    //****************Smartdashboard Buttons**************
    SmartDashboard.putData( "CmdDriveClearAll",  new CmdDriveClearAll());
    SmartDashboard.putData( "DriveToggle",  new CmdDriveTypeToggle());

    SmartDashboard.putData( "0",  new CmdDriveForceTurnAngle(0.0));
    SmartDashboard.putData( "90", new CmdDriveForceTurnAngle(90.0));
    SmartDashboard.putData( "45", new CmdDriveForceTurnAngle(45.0));

  //**********************  AUTOs ****************************************
    m_autoChooser.setDefaultOption("Do Nothing",  new CmdAutoDoNothing() );
    m_autoChooser.addOption(       "Center Auto", new CmdAutoCenter() );
    m_autoChooser.addOption(       "Left Auto",   new CmdAutoLeft() );
    m_autoChooser.addOption(       "Right Auto",  new CmdAutoRight() );

    SmartDashboard.putData("Auto Mode", m_autoChooser);


    configureBindings();
  }

  private void configureBindings() {

    //Driver Buttons
    m_driver.a().onTrue( new CmdPrintText("A Button ON"));
    m_driver.b().onTrue( new CmdPrintText("B Button ON"));

    m_driver.x().onTrue( new CmdDriveForcePark() );
    m_driver.y().onTrue( new CmdDriveForceTurnAngle( 0.0) );

    m_driver.start().onTrue( new CmdDriveZeroGyro() );  


    //Controller Buttons
    // m_controller.povDown().onTrue(new CmdShooterSetPosition(ShooterPositon.IDLE));
    // m_controller.povUp().onTrue(new CmdShooterSetPosition(ShooterPositon.HUB));
    // m_controller.povLeft().onTrue(new CmdShooterSetPosition(ShooterPositon.POS1));
    // m_controller.povRight().onTrue(new CmdShooterSetPosition(ShooterPositon.POS2));

    //Test with power
    m_controller.povDown().onTrue(new CmdShooterSetPower(  SmartDashboard.getNumber("ShooterRpmIdle", 0.0) ));
    m_controller.povUp().onTrue(new   CmdShooterSetPower(  SmartDashboard.getNumber("ShooterRpmHUB", 0.0) ));
    m_controller.povLeft().onTrue(new  CmdShooterSetPower( SmartDashboard.getNumber("ShooterRpmPOS1", 0.0) ));
    m_controller.povRight().onTrue(new CmdShooterSetPower( SmartDashboard.getNumber("ShooterRpmPOS2", 0.0) ));






    m_controller.rightTrigger(0.5)
        .onTrue(new CmdShooterActivate(true))
        .onFalse(new CmdShooterActivate(false));
    m_controller.leftTrigger(0.5)
        .onTrue(new CmdIntakeDeploy())
        .onFalse(new CmdIntakeRetract());
  }



  public Command getAutonomousCommand() {
    return m_autoChooser.getSelected();
  }
}
