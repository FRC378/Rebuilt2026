// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class CmdAutoRight extends SequentialCommandGroup {

  public CmdAutoRight() {
 addCommands(
      new CmdPrintText("**** Auto Center ****"),
      new CmdDriveClearAll(),

      new WaitCommand(1.0),
      //Move Backwards and start prep
      new CmdShooterSetRPM(3000.0),
      new CmdTurretSetAngle (-25.0),
      new CmdDriveToRelativePoint(-36.0, 0.0, 0.0, 0.25, true, 0.0),
    
      //Shoot Ballz
      new WaitCommand(2.0),
      new CmdShooterActivate(true),
  
      //wait for dumping mag
      new WaitCommand(10.0),

      //stop Shooting Ballz
      new CmdShooterActivate(false),
      new CmdShooterSetRPM(0.0),

      new CmdDriveStop(),
      new CmdPrintText("Auto Center Complete")
    );

  
  }
}
