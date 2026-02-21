// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class CmdAutoClimbLeft extends SequentialCommandGroup {
  
  public CmdAutoClimbLeft() {
    
    addCommands(
      new CmdPrintText("**** Auto Left with Climb ****"),
      new CmdDriveClearAll(),

      new WaitCommand(1.0),
      //Move Backwards and start prep
      new CmdShooterSetRPM(3000.0),
      new CmdTurretSetAngle (25.0),
      new CmdDriveToRelativePoint(-36.0, 0.0, 0.0, 0.25, true, 0.0),
    
      //Shoot Ballz
      new WaitCommand(2.0),
      new CmdShooterActivate(true),
  
      //Wait for dumping mag
      new WaitCommand(10.0),

      //Stop Shooting Ballz
      new CmdShooterActivate(false),
      new CmdShooterSetRPM(0.0),

      //Back up to tower
      new CmdDriveToAbsolutePoint(-115.0, 0.0, 0.0, 0.25, true, 0.0), 

      //Drive to tower
      new CmdDriveToRelativePoint(0, -12.0, 0.0, 0.2, true, 2.0),

      //Climb
      new CmdClimbSetPower(0.75),
      new WaitCommand(3.0),
      new CmdClimbSetPower(0.0),


      new CmdDriveStop(),
      new CmdPrintText("Auto Left with Climb Complete")
    );
  }
}
