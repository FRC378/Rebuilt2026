// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class CmdShooterAutoAim extends Command {

  
  public CmdShooterAutoAim() {
    
    addRequirements(RobotContainer.m_Shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("CmdShooterAutoAim Start");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (RobotContainer.m_photonVisionCam1.isTargetValid()) {
      
      double yawError = RobotContainer.m_photonVisionCam1.getTargetYaw();
      double currentAngle = RobotContainer.m_Shooter.TurretGetEncoder();
      double newAngle = currentAngle + yawError;
      RobotContainer.m_Shooter.TurretPosition(newAngle);
    }

  }




  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
      System.out.println("CmdShooterAutoAim End");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //keep running until AutoAim button is released
    return false;
  }
}
