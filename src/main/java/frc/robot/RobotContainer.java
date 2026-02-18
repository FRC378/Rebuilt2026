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
import frc.robot.commands.CmdShooterActivate;
import frc.robot.commands.CmdShooterDefault;
import frc.robot.commands.CmdShooterSetPosition;
import frc.robot.commands.CmdShooterSetPosition.ShooterPositon;
import frc.robot.commands.CmdClimberDefault;
import frc.robot.commands.CmdIntakeDeploy;
import frc.robot.commands.CmdIntakeRetract;
//Subsystems
import frc.robot.subsystems.Handler;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Climber;



public class RobotContainer {

  //****************Controllers*******************
  public static final CommandXboxController m_driver = new CommandXboxController(0);
  public static final CommandXboxController m_controller = new CommandXboxController(1);

  //****************Subsystems*******************
  public static PowerDistribution m_pdp  = new PowerDistribution(Constants.PDP_CAN_ID, PowerDistribution.ModuleType.kCTRE);
  public static Climber m_climber = new Climber();
  public static Intake m_intake = new Intake ();
  public static Handler m_handler = new Handler();
  public static Shooter m_Shooter = new Shooter();

  public RobotContainer() {

    //****************Default Commands**************
    m_climber.setDefaultCommand(new CmdClimberDefault());
    m_Shooter.setDefaultCommand(new CmdShooterDefault());
    //****************Smartdashboard Buttons**************

    configureBindings();
  }

  private void configureBindings() {

    //Driver Buttons
    m_driver.a().onTrue( new CmdPrintText("A Button ON"));
    m_driver.b().onTrue( new CmdPrintText("B Button ON"));

    //Controller Buttons
    m_controller.povDown().onTrue(new CmdShooterSetPosition(ShooterPositon.IDLE));
    m_controller.povUp().onTrue(new CmdShooterSetPosition(ShooterPositon.HUB));
    m_controller.povLeft().onTrue(new CmdShooterSetPosition(ShooterPositon.POS1));
    m_controller.povRight().onTrue(new CmdShooterSetPosition(ShooterPositon.POS2));
    m_controller.rightTrigger(0.5)
        .onTrue(new CmdShooterActivate(true))
        .onFalse(new CmdShooterActivate(false));
    m_controller.leftTrigger(0.5)
        .onTrue(new CmdIntakeDeploy())
        .onFalse(new CmdIntakeRetract());
  }



  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
