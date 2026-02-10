// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.units.measure.Power;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private SparkMax m_collectorMotor;
  private SparkMax m_armMotor; 
  private RelativeEncoder m_armEncoder; 
  private boolean m_theOneTrueFlag; 

  // MLR is Maximum Lower range 
  // Mur is maximum upper range

  private final double FULLRANGE = 20.0;
  private final double MUR = FULLRANGE * 0.25;
  private final double MLR = FULLRANGE * 0.75;



  
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
    
    SmartDashboard.putNumber("collectorpower" , 0.5 );

  }

  @Override
  public void periodic() {
   SmartDashboard.putBoolean("IntakeTopSwitch", IntakeUpperSwitch());
   SmartDashboard.putNumber("ArmEncoders", ArmEncoder());

    if(IntakeUpperSwitch()== true && m_armEncoder.getPosition()>1.0 )
   {
    m_armEncoder.setPosition (0);
   }


   double collectorPower = SmartDashboard.getNumber ("collectorpower" , 0.5 );
   double currentPosition = ArmEncoder();

    if (m_theOneTrueFlag == true){

      if (currentPosition > MUR) {
        ArmGo (0.75); 
        CollectorGo(0.0);
      }
      else if (currentPosition > MLR) { 
        ArmGo (0.25); 
        CollectorGo(collectorPower);
      }
      else {
        ArmGo (0.0);
        CollectorGo(collectorPower);
      }
    }
    else {

      if (currentPosition < MLR) {
        ArmGo (-0.75); 
        CollectorGo (collectorPower);
      }
      else if (currentPosition < MUR) { 
        ArmGo (-0.25); 
        CollectorGo (0.0);
      }
      else {
        ArmGo (-0.0);
        CollectorGo (0.0);
      }
    }

  }
  public void ArmGo(double power) {
    m_armMotor.set(power);}

  public void CollectorGo(double power) {
    m_collectorMotor.set(power);}

  public void IntakeAllStop() {
    m_armMotor.set(0.0); 
    m_collectorMotor.set(0.0);
  }

  public boolean IntakeUpperSwitch() {
    return m_armMotor.getForwardLimitSwitch().isPressed();
  }

   public double ArmEncoder() {
    return m_armEncoder.getPosition();
  }

  public void SetDeplopyFlag( boolean deplopy) { 
    m_theOneTrueFlag = deplopy;
  }
  public boolean GetDeplopyFlag() { 
    return m_theOneTrueFlag;
  }








}
