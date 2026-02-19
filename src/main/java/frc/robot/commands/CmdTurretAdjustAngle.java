// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;


public class CmdTurretAdjustAngle extends InstantCommand {
  private double m_adjust;

  public CmdTurretAdjustAngle(double adjust) {
    m_adjust = adjust;
  }

  
  @Override
  public void initialize() {
    double currentAngle = RobotContainer.m_Shooter.TurretGetEncoder();
    double newAngle = currentAngle + m_adjust;
    RobotContainer.m_Shooter.TurretPosition(newAngle);
  }
}
