// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class CmdAutoRightOutpost extends SequentialCommandGroup {

  public CmdAutoRightOutpost() {
  addCommands(
      new CmdPrintText("**** Auto Right Outpost ****"),
      new CmdDriveClearAll(),

      new WaitCommand(1.0),

      //Move Backwards Towards Outpos while truning 90 degrees
      new CmdDriveToRelativePoint(-100.0, 0.0, 90.0, 0.4, false, 0.0),
//new CmdIntakeDeploy(),      
      new CmdDriveToRelativePoint(-25.0, 0.0, 90.0, 0.2, true,   5.0),

      //Wait for balls to load from HP
      new WaitCommand(3.0),


      //Drive toward HUB
      new CmdShooterSetRPM(2000.0),          
      new CmdDriveToRelativePoint(50.0, 50.0, 45.0, 0.25, true, 0.0),

 
      //Shoot Ballz
      new WaitCommand(2.0),
      new CmdShooterActivate(true),
  
      //wait for dumping mag
      new WaitCommand(5.0),

      //stop Shooting Ballz
      new CmdShooterActivate(false),
      new CmdShooterSetRPM(0.0),

      new CmdDriveStop(),
      new CmdPrintText("Auto Right Outpost Complete")
    );

  }
}
