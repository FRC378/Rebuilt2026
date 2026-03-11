// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

  private SparkMax m_turretMotor;
  private SparkMax m_shooterMotor1;
  private SparkMax m_shooterMotor2;
  private RelativeEncoder m_turretEncoder;
  private RelativeEncoder m_shooterEncoder;
  private SparkClosedLoopController m_turretPID;
  private SparkClosedLoopController m_shooterPID;
  

  public Shooter() {
    m_turretMotor = new SparkMax (Constants.TURRET_MOTOR_CAN_ID , MotorType.kBrushless);
    m_turretEncoder = m_turretMotor.getEncoder();
    m_turretPID = m_turretMotor.getClosedLoopController();

    m_shooterMotor1 = new SparkMax( Constants.SHOOTER_MOTOR1_CAN_ID , MotorType.kBrushless);
    m_shooterMotor2 = new SparkMax(Constants.SHOOTER_MOTOR2_CAN_ID , MotorType.kBrushless);
    m_shooterEncoder = m_shooterMotor1.getEncoder();
    m_shooterPID     = m_shooterMotor1.getClosedLoopController();


    //----   TURRET CONFIG ------------------------

    SparkMaxConfig turretMotorConfig = new SparkMaxConfig(); 

      turretMotorConfig
        .smartCurrentLimit(40)
        .idleMode(IdleMode.kBrake)
        .inverted(false) 
        .openLoopRampRate(0.0);

      turretMotorConfig.encoder
        .positionConversionFactor(0.70); //90deg = 118 revs
      

      turretMotorConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .pid(0.35, 0, 0)
        .outputRange(-0.9,0.9);

      m_turretMotor.configure(turretMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);




    //----   SHOOTER CONFIG ------------------------

   SparkMaxConfig shooterMotor1Config = new SparkMaxConfig(); 

    shooterMotor1Config
      .smartCurrentLimit(40)
      .idleMode(IdleMode.kCoast)
      .inverted(true) 
      .openLoopRampRate(0.5);

    shooterMotor1Config.encoder
      .positionConversionFactor(1.0)
      .velocityConversionFactor(1.0);

    shooterMotor1Config.closedLoop
      .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
      .pid(0.0001, 0, 0)
      .velocityFF(0.00205)         // 4580 @ 0.8    
      .outputRange(0,0.95);

    m_shooterMotor1.configure(shooterMotor1Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);


   SparkMaxConfig shooterMotor2Config = new SparkMaxConfig(); 

    shooterMotor2Config
      .smartCurrentLimit(40)
      .idleMode(IdleMode.kCoast)
      .follow(Constants.SHOOTER_MOTOR1_CAN_ID,true);

    m_shooterMotor2.configure(shooterMotor2Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);



    SmartDashboard.putNumber("ShooterRpmIdle", 0.0);
    SmartDashboard.putNumber("ShooterRpmHUB", 2500.0);
    SmartDashboard.putNumber("ShooterRpmPOS1", 4000.0);
    SmartDashboard.putNumber("ShooterRpmPOS2", 3000.0);

    SmartDashboard.putNumber("Manual Turret Angle", 0.0);



  }

  @Override
  public void periodic() {

    SmartDashboard.putNumber("TurretEncoder",   TurretGetEncoder());
    SmartDashboard.putNumber("ShooterVelocity", GetShooterVelocity());

  }


public void TurretGo (double power) {

  double currAngle = TurretGetEncoder();
  double currPower = power;

  if(currAngle > 80.0 && currPower > 0.0) {
    currPower = 0.0;
  }
  if(currAngle < -80.0 && currPower < 0.0) {
    currPower = 0.0;
  }

  m_turretMotor.set(currPower);
}

public void TurretPosition (double angle) {

  //Check we don't exceed our limits
  double adjAngle = angle;

  if(angle > 80.0) {
    adjAngle = 80.0;
  }
  if(angle < -80.0) {
    adjAngle = -80.0;
  }
  
  m_turretPID.setReference(adjAngle, ControlType.kPosition);
}

public void TurretStop () {
  m_turretMotor.set (0.0);
}


public double TurretGetEncoder() {
  return m_turretEncoder.getPosition();
}

public void TurretSetEncoder(double angle) {
  m_turretEncoder.setPosition(angle);
  System.out.println("Turret Set Encoder: " + angle);
}





public void ShooterGo (double power) {
  m_shooterMotor1.set (power);
  //m_shooterMotor2.set (0);
}

public void ShooterStop () {
  m_shooterMotor1.set (0.0); 
  m_shooterMotor2.set (0.0);
}

public void SetShooterVelocity (double rpm) {
  m_shooterPID.setReference(rpm, ControlType.kVelocity);
}

public double GetShooterVelocity () {
  return m_shooterEncoder.getVelocity();
}




}