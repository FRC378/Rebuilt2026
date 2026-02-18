// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;


public class CmdShooterActivate extends InstantCommand {
  private boolean m_activate;
  public CmdShooterActivate(boolean activate) {
    m_activate = activate; 


  }


  @Override
  public void initialize() {
   double elevatorGo = SmartDashboard.getNumber ("Elevator Motor Power" , 0.5 );
   double transportGo = SmartDashboard.getNumber ("Transport Motor Power" , 0.5);



    if (m_activate == true) {
      RobotContainer.m_handler.ElevatorGo(elevatorGo);
      RobotContainer.m_handler.TransportGo(transportGo);
    }

    if (m_activate == false) {
      RobotContainer.m_handler.ElevatorHalt();
      RobotContainer.m_handler.TransportHalt();
    }




  }
}
