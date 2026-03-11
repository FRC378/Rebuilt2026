// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class CmdAutoCenter extends SequentialCommandGroup {

  public CmdAutoCenter() {
  addCommands(
      new CmdPrintText("**** Auto Center ****"),
      new CmdDriveClearAll(),
      new CmdIntakeSetArmEncoder(),

      new WaitCommand(1.0),
      //Move Backwards and start prep


      //Back away from Hub
      new CmdDriveToRelativePoint(-10.0, 0.0, 0.0, 0.35, false, 0.0),

      new CmdIntakeDeploy(),
      new CmdShooterSetRPM(3000.0),
      new CmdDriveToAbsolutePoint(-70.0, 0.0, 0.0, 0.35, true, 0.0),


      //Shoot Ballz
      new WaitCommand(3.0),
      new CmdShooterActivate(true),
  
      //wait for dumping mag
      new WaitCommand(5.0),

      //stop Shooting Ballz
      new CmdShooterActivate(false),
      new CmdShooterSetRPM(0.0),

      new CmdDriveStop(),
      new CmdPrintText("Auto Center Complete")
    );

  }
}
