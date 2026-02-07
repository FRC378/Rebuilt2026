// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Constants;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private SparkMax m_climberMotor; 
  private RelativeEncoder m_climberEncoder; 





  public Climber() {
    m_climberMotor = new SparkMax(Constants.CLIMBER_MOTOR_CAN_ID , MotorType.kBrushless); 
    m_climberEncoder = m_climberMotor.getEncoder(); 

    SparkMaxConfig climberMotorConfig = new SparkMaxConfig(); 

    climberMotorConfig
      .smartCurrentLimit(40)
      .idleMode(IdleMode.kBrake)
      .inverted(false) 
      .openLoopRampRate(0.5);

    m_climberMotor.configure(climberMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters); 
  }


  @Override
  public void periodic() {
    SmartDashboard.putBoolean("ClimberTopSwitch", ClimberUpperSwitch());
    SmartDashboard.putBoolean("ClimberBottomSwitch", ClimberLowerSwitch());
    SmartDashboard.putNumber("ClimberEncoders", ClimberEncoder());
  }

  public void ClimberStop() {
    m_climberMotor.set(0.0);
  }

  public void ClimberGo(double power) {
    m_climberMotor.set(power);
  }

  public boolean ClimberUpperSwitch() {
    return m_climberMotor.getForwardLimitSwitch().isPressed();
  }

  public boolean ClimberLowerSwitch() {
    return m_climberMotor.getReverseLimitSwitch().isPressed();
  }

  public double ClimberEncoder() {
    return m_climberEncoder.getPosition();
  }




}
