// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class CmdShooterSetPosition extends InstantCommand {

  public enum ShooterPositon {
    IDLE, HUB, POS1, POS2
  }
  ShooterPositon m_positon; 


  public CmdShooterSetPosition(ShooterPositon pos) {
    m_positon = pos;
  }

  
  @Override
  public void initialize() {

    double home = SmartDashboard.getNumber("ShooterRpmHome", 1000.0);
    double hub = SmartDashboard.getNumber("ShooterRpmHUB", 1000.0);
    double pos1 = SmartDashboard.getNumber("ShooterRpmPOS1", 1000.0);
    double pos2 = SmartDashboard.getNumber("ShooterRpmPOS2", 1000.0);


    if(m_positon  == ShooterPositon.IDLE){
      RobotContainer.m_Shooter.SetShooterVelocity(home);
    }

    if(m_positon  == ShooterPositon.HUB){
      RobotContainer.m_Shooter.SetShooterVelocity(hub);
    }

    if(m_positon  == ShooterPositon.POS1){
      RobotContainer.m_Shooter.SetShooterVelocity(pos1);
    }

    if(m_positon  == ShooterPositon.POS2){
      RobotContainer.m_Shooter.SetShooterVelocity(pos2);
    }
  }
}
