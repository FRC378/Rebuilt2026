// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import java.util.stream.Collector;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private SparkMax m_collectorMotor;
  private SparkMax m_armMotor; 
  private RelativeEncoder m_armEncoder; 


  
  public Intake() {
    m_collectorMotor = new SparkMax(Constants.COLLECTOR_MOTOR_CAN_ID , MotorType.kBrushless);
    m_armMotor = new SparkMax(Constants.ARM_MOTOR_CAN_ID , MotorType.kBrushless); 
    m_armEncoder = m_armMotor. getEncoder();

    SparkMaxConfig collectorMotorConfig = new SparkMaxConfig(); 

    collectorMotorConfig
      .smartCurrentLimit(40)
      .idleMode(IdleMode.kCoast)
      .inverted(false) 
      .openLoopRampRate(0.0);

    m_collectorMotor.configure(collectorMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    SparkMaxConfig armMotorConfig = new SparkMaxConfig(); 

    armMotorConfig
      .smartCurrentLimit(40)
      .idleMode(IdleMode.kBrake)
      .inverted(false) 
      .openLoopRampRate(0.0);

    m_armMotor.configure(armMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }



  @Override
  public void periodic() {
  


  }
  public void ArmGo(double power) {
    m_armMotor.set(power);}

  public void CollectorGo(double power) {
    m_collectorMotor.set(power);}

  public void IntakeAllStop() {
    m_armMotor.set(0.0); 
    m_collectorMotor.set(0.0);
  }

  
}
