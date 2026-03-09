// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;


public class CmdTurretSetAngleManual extends InstantCommand {

  public CmdTurretSetAngleManual() {
  }

  
  @Override
  public void initialize() {


    double angle = SmartDashboard.getNumber("Manual Turret Angle", 0.0);
    RobotContainer.m_Shooter.TurretPosition(angle);
  }

}
