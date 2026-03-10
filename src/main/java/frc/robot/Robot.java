// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

// Team programers Andrew C, Cash L, Evan M

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;

  public Robot() {

    //********************** INIT **************************
    System.out.println(" ~~~ Rebuilt 2026 ~~~~ ");    
    System.out.println("Robot Init");


    //Subsystem Initialization
    RobotContainer.m_Shooter.TurretSetEncoder(0.0);   //Assume turret is centered at start up
    RobotContainer.m_Shooter.ShooterStop();
    RobotContainer.m_handler.TransportHalt();
    RobotContainer.m_intake.IntakeAllStop();

    //Reset Swerve
    RobotContainer.m_drivetrain.ResetDriveEncoders();
    RobotContainer.m_drivetrain.ResetTurnEncoders();
    RobotContainer.m_drivetrain.ZeroGyro();
    RobotContainer.m_drivetrain.ResetOdometry();


    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    WriteToSmartDashboard();
  }

  @Override
  public void disabledInit() {
    System.out.println("DisabledInit");

    CheckAlliance();

    //Turn off motors if disabled
    RobotContainer.m_Shooter.ShooterStop();
    RobotContainer.m_Shooter.TurretStop();
    RobotContainer.m_intake.ClearFlags();



  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    System.out.println("AutonomousInit");
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      CommandScheduler.getInstance().schedule(m_autonomousCommand);
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    System.out.println("TeleopInit");
    CheckAlliance();
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}



  //---------------------------------------------------------------------------
  public void WriteToSmartDashboard() {

    SmartDashboard.putNumber("Match Time", Timer.getMatchTime() );
    SmartDashboard.putNumber("TimeStamp",  Timer.getTimestamp() );
    SmartDashboard.putNumber("Volts", RobotContainer.m_pdp.getVoltage() );


    //******   XBox Controller
    SmartDashboard.putNumber("LeftX",   RobotContainer.m_driver.getLeftX() );
    SmartDashboard.putNumber("LeftY",   RobotContainer.m_driver.getLeftY() );
    SmartDashboard.putNumber("RightX",  RobotContainer.m_driver.getRightX() );
    SmartDashboard.putNumber("RightY",  RobotContainer.m_driver.getRightY() );



  }


  public void CheckAlliance() {
    var alliance = DriverStation.getAlliance();
    
    if (alliance.isPresent()) {
      if (alliance.get() == Alliance.Red) {
        SmartDashboard.putString("Alliance", "Red");
        System.out.println("Alliance: Red");
        RobotContainer.m_photonVisionCam1.setTargetId(10);
      } else {
        SmartDashboard.putString("Alliance", "Blue");
        System.out.println("Alliance: Blue");
        RobotContainer.m_photonVisionCam1.setTargetId(26);
      }
    } else {
      SmartDashboard.putString("Alliance", "Unknown");
      System.out.println("Alliance: Unknown");
    }
  }


}
