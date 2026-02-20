// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class CmdDriveStop extends InstantCommand {
  public CmdDriveStop() {
    
    // METHOD_STUB
    System.out.println("METHOD STUB");
  }


  @Override
  public void initialize() {
    System.out.println("CmdDriveStop");
  }
}
