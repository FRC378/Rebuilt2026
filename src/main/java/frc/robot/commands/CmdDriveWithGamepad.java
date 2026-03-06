// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;


public class CmdDriveWithGamepad extends Command {
  /** Creates a new CmdDriveWithGamepad. */
  public CmdDriveWithGamepad() {

    addRequirements(RobotContainer.m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("CmdDriveWithGamepad Init");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    final double DEADBAND = 0.05;

    double leftY  = -edu.wpi.first.math.MathUtil.applyDeadband(RobotContainer.m_driver.getLeftY(),  DEADBAND);
    double leftX  = -edu.wpi.first.math.MathUtil.applyDeadband(RobotContainer.m_driver.getLeftX(),  DEADBAND);
    double rightX = -edu.wpi.first.math.MathUtil.applyDeadband(RobotContainer.m_driver.getRightX(), DEADBAND);

    boolean rightBumper = RobotContainer.m_driver.rightBumper().getAsBoolean();
    boolean leftBumper  = RobotContainer.m_driver.leftBumper().getAsBoolean();


    //Define default Drive Power
    double xyScaleValue  = 0.4;
    double rScaleValue   = 0.4;  

    //Creep Mode
    if( leftBumper )
    {
      xyScaleValue  = 0.3;
      rScaleValue   = 0.3;  
    }
    //Turbo Mode
    else if( rightBumper )
    {
      xyScaleValue  = 0.5;
      rScaleValue   = 0.5;  
    }


    //Apply scaling
    double fwdrev    = leftY  * xyScaleValue; 
    double rightleft = leftX  * xyScaleValue;    
    double rotate    = rightX * rScaleValue;


  // double rightTrigger = RobotContainer.m_driver.getRightTriggerAxis();   

  // //Use PhotonVision to aim, overwriting rotate parameter
  // if( rightTrigger > 0.9 )
  // {
  //   double deltaAngle = RobotContainer.m_photonVisionCam1.getTargetYaw();
  //   double turnVelocity = deltaAngle / 20.0;         //turnVelocity Range -1.0 to +1.0
  //   double maxTurnVelocity = 0.75;  

  //   if( turnVelocity >  maxTurnVelocity ) turnVelocity =  maxTurnVelocity;
  //   if( turnVelocity < -maxTurnVelocity ) turnVelocity = -maxTurnVelocity;
    
  //   rotate = -turnVelocity;
  // }


    //Call Drive!
    RobotContainer.m_drivetrain.Drive(fwdrev, rightleft, rotate, RobotContainer.m_drivetrain.GetDriveType() );

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("CmdDriveWithGamepad End");

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; //Never ends!
  }
}
