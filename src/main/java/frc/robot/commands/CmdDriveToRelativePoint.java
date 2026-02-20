// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;


//   Drive to coordinate based on current robot position
//       
//                         ^  +x
//               front     |
//             +-------+   |
//             |       |   |
//             |   +   |   |
//             |       |   |
//             +-------+   |
//                         |
//     +y <----------------0
//
//   Coordinate System:   
//      +X = Forward
//      +Y = Left
//      +r = CCW rotation
//
//  Heading: Direction FRONT should face during maneuver
//  Speed:   % max speed (range 0 - 1.0)
//  Stop:    Stop when point reached?
//  Timeout: Seconds until timeout (0=disabled)
//
public class CmdDriveToRelativePoint extends Command {

  private double m_finalX;
  private double m_finalY;
  private double m_finalH;

  private double m_speed;
  private boolean m_stop;
  private double m_timeout;

  private boolean m_closeEnough;

  private final Timer m_timer = new Timer();


  public CmdDriveToRelativePoint(double x, double y, double heading, double speed, boolean stop, double timeout) {
    m_finalX = x;
    m_finalY = y;
    m_finalH = heading;

    m_speed = speed;   //range [0:1.0] - Percent of maximum drive speed
    m_stop = stop;
    m_timeout = timeout;

    m_closeEnough = false;

    // METHOD_STUB
    System.out.println("METHOD STUB");
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
