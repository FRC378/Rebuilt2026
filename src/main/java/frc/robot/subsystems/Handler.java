// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class Handler extends SubsystemBase {
  
  private SparkMax m_transportMotor;
  private SparkMax m_elevatorMotor; 
 
  public Handler() {
    m_transportMotor = new SparkMax (Constants.TRANSPORT_MOTOR_CAN_ID , MotorType.kBrushless);
    m_elevatorMotor = new SparkMax (Constants.ELEVATOR_MOTOR_CAN_ID , MotorType.kBrushless);


    SparkMaxConfig transportMotorConfig = new SparkMaxConfig();

    transportMotorConfig
      .smartCurrentLimit(40)
      .idleMode(IdleMode.kCoast)
      .inverted(false) 
      .openLoopRampRate(0.0);
    m_transportMotor.configure(transportMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

     SparkMaxConfig elevatorMotorConfig = new SparkMaxConfig(); 

    elevatorMotorConfig
      .smartCurrentLimit(40)
      .idleMode(IdleMode.kBrake)
      .inverted(false) 
      .openLoopRampRate(0.0);

    m_elevatorMotor.configure(elevatorMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    SmartDashboard.putNumber ("Elevator Motor Power" , 0.5 );
    SmartDashboard.putNumber ("Transport Motor Power" , 0.5);





  }



  @Override
  public void periodic() {
  
  }

  public void TransportGo(double power) {
    m_transportMotor.set(power);
  }
  public void ElevatorGo(double power) {
    m_elevatorMotor.set(power);
  }
  public void ElevatorHalt() {
    m_elevatorMotor.set(0.0);
  }
  public void TransportHalt() {
    m_transportMotor.set(0.0);
  }
  





}
