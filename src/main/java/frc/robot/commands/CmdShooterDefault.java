// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;


public class CmdShooterDefault extends Command {
  private boolean m_turretManualFlag;
  public CmdShooterDefault() {
    m_turretManualFlag = false;
    addRequirements(RobotContainer.m_Shooter);
  }

  
  @Override
  public void initialize() {
    System.out.println("CmdShooterDefaultStart");
  }

 
  @Override
  public void execute() {

    //Turret Manual
    if(RobotContainer.m_controller.x().getAsBoolean()){
      m_turretManualFlag = true;
      RobotContainer.m_Shooter.TurretGo(RobotContainer.m_controller.getLeftX());
    }
    else if(m_turretManualFlag == true){
      RobotContainer.m_Shooter.TurretStop();
      m_turretManualFlag = false;
    }  











  }

  
  @Override
  public void end(boolean interrupted) {
    System.out.println("CmdShooterDefaultEnd");
  }

 
  @Override
  public boolean isFinished() {
    return false;
  }
}
