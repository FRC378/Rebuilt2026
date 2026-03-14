// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;


public class CmdStealBalls extends Command {

  private double m_lockedFieldHeading;  //field-relative heading to shoot
  private boolean m_isSafe = true;

  private static final double TURRET_MIN_ANGLE = -70.0;
  private static final double TURRET_MAX_ANGLE =  70.0;
  private static final double TURRET_AIM_TOLERANCE = 2.0;  // degrees

  public CmdStealBalls() {

    addRequirements(RobotContainer.m_Shooter, RobotContainer.m_handler);
  }

  @Override
  public void initialize() {

    double currentTurretAngle = RobotContainer.m_Shooter.TurretGetEncoder();
    double currentGyroYaw     = RobotContainer.m_drivetrain.GetGyroYaw();

    m_lockedFieldHeading = currentTurretAngle - currentGyroYaw;

    m_isSafe   = true;

    //Start shooter
    RobotContainer.m_Shooter.SetShooterVelocity(2500);
    RobotContainer.m_handler.TransportGo(0.7);
  }

  @Override
  public void execute() {

    //Get current gyro
    double currentGyroYaw = RobotContainer.m_drivetrain.GetGyroYaw();

    //Calculate turrent angle needed
    double rawTurretAngle = m_lockedFieldHeading + currentGyroYaw;

    // Wrap 180
    double wrappedTurretAngle = MathUtil.inputModulus(rawTurretAngle, -180.0, 180.0);

    // Check if the required angle is within turret range
    boolean withinLimits = (wrappedTurretAngle >= TURRET_MIN_ANGLE)
                        && (wrappedTurretAngle <= TURRET_MAX_ANGLE);


    if (withinLimits) {

      //Update Turret position
      RobotContainer.m_Shooter.TurretPosition(wrappedTurretAngle);

      // Check if turret has physically arrived within tolerance
      double currentTurretAngle = RobotContainer.m_Shooter.TurretGetEncoder();
      boolean onTarget = Math.abs(currentTurretAngle - wrappedTurretAngle) <= TURRET_AIM_TOLERANCE;

      if (onTarget && !m_isSafe) {
        // Turret on target — SHOOT!!!
        RobotContainer.m_handler.TransportGo(0.7);
        m_isSafe = true;


      } else if (!onTarget && m_isSafe) {
        // Turret is moving but not on target yet
        RobotContainer.m_handler.TransportHalt();
        m_isSafe = false;
      }

    } else {
      // Turret can't reach the heading - STOP!!!!!!
      if (m_isSafe) {
        RobotContainer.m_handler.TransportHalt();
      }
      m_isSafe = false;
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_isSafe   = true;
    RobotContainer.m_handler.TransportHalt();
    RobotContainer.m_Shooter.ShooterWindDown();
  }

  @Override
  public boolean isFinished() {
    return false; // Runs until button released
  }



}
