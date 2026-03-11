// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

public class CmdShooterAim extends InstantCommand {

  public CmdShooterAim() {
    addRequirements(RobotContainer.m_Shooter);
  }

  @Override
  public void initialize() {
    System.out.println("CmdShooterAim Start");

    // One-time target read and turret adjust
    if (RobotContainer.m_photonVisionCam1.isTargetValid()) {
      double yawError = RobotContainer.m_photonVisionCam1.getTargetYaw();
      double currentAngle = RobotContainer.m_Shooter.TurretGetEncoder();
      double newAngle = currentAngle + yawError;
      RobotContainer.m_Shooter.TurretPosition(newAngle);
      System.out.println("CmdShooterAim -> Angle: " + newAngle);
    } else {
      System.out.println("CmdShooterAim -> No valid target");
    }
  }
}
