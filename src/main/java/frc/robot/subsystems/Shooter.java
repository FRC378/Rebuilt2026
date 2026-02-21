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
   SparkMaxConfig turretMotorConfig = new SparkMaxConfig(); 

    turretMotorConfig
      .smartCurrentLimit(40)
      .idleMode(IdleMode.kBrake)
      .inverted(false) 
      .openLoopRampRate(0.0);

    turretMotorConfig.encoder
      .positionConversionFactor(1.0); //do some math and fix this line later
    

    turretMotorConfig.closedLoop
      .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
      .pid(0, 0, 0)
      .outputRange(-0.5,0.5);

    m_turretMotor.configure(turretMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);


   SparkMaxConfig shooterMotor1Config = new SparkMaxConfig(); 

    shooterMotor1Config
      .smartCurrentLimit(40)
      .idleMode(IdleMode.kCoast)
      .inverted(false) 
      .openLoopRampRate(0.0);

    shooterMotor1Config.encoder
      .positionConversionFactor(1.0)
      .velocityConversionFactor(1.0);

    shooterMotor1Config.closedLoop
      .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
      .pid(0, 0, 0)
      .outputRange(0,0.95);

    m_shooterMotor1.configure(shooterMotor1Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);


   SparkMaxConfig shooterMotor2Config = new SparkMaxConfig(); 

    shooterMotor2Config
      .smartCurrentLimit(40)
      .idleMode(IdleMode.kCoast)
      .inverted(false) 
      .openLoopRampRate(0.0)
      .follow(Constants.SHOOTER_MOTOR1_CAN_ID);

    m_shooterMotor2.configure(shooterMotor2Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);


    SmartDashboard.putNumber("ShooterRpmHome", 1000.0);
    SmartDashboard.putNumber("ShooterRpmHUB", 1000.0);
    SmartDashboard.putNumber("ShooterRpmPOS1", 1000.0);
    SmartDashboard.putNumber("ShooterRpmPOS2", 1000.0);





  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("TurretL", TurretLSideSwitch());
    SmartDashboard.putBoolean("TurretR", TurretRSideSwitch());
    SmartDashboard.putNumber("TurretEncoder", TurretGetEncoder());

    if(TurretLSideSwitch() == true){
      TurretSetEncoder(-90.0);
    }

    if(TurretRSideSwitch() == true){
      TurretSetEncoder(90.0);
    }







  }

public void TurretGo (double power) {
  m_turretMotor.set(power);
}

public void TurretPosition (double angle) {
  m_turretPID.setSetpoint(angle, ControlType.kPosition);
}

public void TurretStop () {
  m_turretMotor.set (0.0);
}

public boolean TurretLSideSwitch() {
    return m_turretMotor.getForwardLimitSwitch().isPressed();
}

public boolean TurretRSideSwitch() {
    return m_turretMotor.getReverseLimitSwitch().isPressed();
}

public double TurretGetEncoder() {
  return m_turretEncoder.getPosition();
}

public void TurretSetEncoder(double angle) {
  m_turretEncoder.setPosition(angle);
}




public void ShooterGo (double power) {
  m_shooterMotor1.set (power);
  //m_shooterMotor2.set (power);
}

public void ShooterStop () {
  m_shooterMotor1.set (0.0); 
  m_shooterMotor2.set (0.0);
}

public void SetShooterVelocity (double rpm) {
  m_shooterPID.setSetpoint(rpm, ControlType.kVelocity);
}

public double GetShooterVelocity () {
  return m_shooterEncoder.getVelocity();
}




}