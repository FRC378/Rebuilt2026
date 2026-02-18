// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class CmdClimberDefault extends Command {

  public CmdClimberDefault() {
    addRequirements(RobotContainer.m_climber);
  }

  @Override
  public void initialize() {
    System.out.println("CmdClimberDefaultStart");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double value = RobotContainer.m_driver.getLeftY();
    boolean button = RobotContainer.m_driver.x().getAsBoolean();
    if(button) {
      RobotContainer.m_climber.ClimberGo(value);
    }
    


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("CmdClimberDefaultEnd");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
