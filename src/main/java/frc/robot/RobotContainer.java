// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


//Commands
import frc.robot.commands.CmdPrintText;

//Subsystems



public class RobotContainer {

  //****************Controllers*******************
  public static final CommandXboxController m_driver = new CommandXboxController(0);


  //****************Subsystems*******************
  public static PowerDistribution m_pdp  = new PowerDistribution(Constants.PDP_CAN_ID, PowerDistribution.ModuleType.kCTRE);





  public RobotContainer() {

    //****************Default Commands**************

    //****************Smartdashboard Buttons**************

    configureBindings();
  }

  private void configureBindings() {

    //Driver Buttons
    m_driver.a().onTrue( new CmdPrintText("A Button ON"));
    m_driver.b().onTrue( new CmdPrintText("B Button ON"));



  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
