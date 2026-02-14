// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;


public class CmdTurretSetAngle extends InstantCommand {
  private double m_angle;

  public CmdTurretSetAngle(double angle) {
    m_angle = angle;
  }

  
  @Override
  public void initialize() {
    RobotContainer.m_Shooter.TurretPosition(m_angle);
  }

}
