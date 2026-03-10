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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Intake extends SubsystemBase {
  private SparkMax m_collectorMotor;
  private SparkMax m_armMotor; 
  private RelativeEncoder m_armEncoder; 
  private boolean m_theOneTrueFlag; 
  private boolean m_goHomeFlag;


  // MLR is Maximum Lower range 
  // Mur is maximum upper range

  private final double FULLRANGE = 60.0;
  private final double MUR = FULLRANGE * 0.25;
  private final double MLR = FULLRANGE * 0.75;



  
  public Intake() {
    m_collectorMotor = new SparkMax(Constants.COLLECTOR_MOTOR_CAN_ID , MotorType.kBrushless);
    m_armMotor = new SparkMax(Constants.ARM_MOTOR_CAN_ID , MotorType.kBrushless); 
    m_armEncoder = m_armMotor. getEncoder();
    m_theOneTrueFlag = false; 
    m_goHomeFlag = false;


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
      .inverted(true) 
      .openLoopRampRate(0.0);

    armMotorConfig.encoder
      .positionConversionFactor(1.0)
      .velocityConversionFactor(1.0);

    m_armMotor.configure(armMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
    SmartDashboard.putNumber("collectorpower" , 0.9 );

  }

  @Override
  public void periodic() {


    SmartDashboard.putNumber("ArmEncoders", ArmEncoder());
    SmartDashboard.putNumber("ArmCurrent", m_armMotor.getOutputCurrent());
    

    if(IntakeUpperSwitch()== true && m_armEncoder.getPosition()>1.0 )
    {
      m_armEncoder.setPosition (0);
      System.out.println("Set ARM Encoder=0");
    }


    double collectorPower = SmartDashboard.getNumber ("collectorpower" , 0.5 );


    // if (RobotContainer.m_controller.y().getAsBoolean()) {
    //   collectorPower = collectorPower * -0.5;
    // }

    if (RobotContainer.m_controller.y().getAsBoolean()) {
      CollectorGo (collectorPower);
    }
    else {
      CollectorGo(0.0);
    }

   double currentPosition = ArmEncoder();


    //GO HOME Commnd
    if (m_goHomeFlag == true){
      ArmGo (0.25);
      CollectorGo (0.0);
      if ( IntakeUpperSwitch () == true) {
        ArmGo (0.0);
        CollectorGo (0.0);
        m_goHomeFlag = false;
      }

    }
    //MANUAL CONTROL
    else if ( RobotContainer.m_controller.x().getAsBoolean() ) {

      ArmGo( RobotContainer.m_controller.getRightY() / 3.0 );
      CollectorGo(0.0);

    }

    // else if (m_theOneTrueFlag == true){

    //   CollectorGo(collectorPower);
    // }
    // else {

    //   CollectorGo(0.0);
    // }


    else if (m_theOneTrueFlag == true){

      // GOING DOWN (DEPLOY)
      if( currentPosition < FULLRANGE ) {
        ArmGo (0.3);
      }
      else {
        ArmGo (0.0);
      }
    }

    else {
      //Going UP

      ArmGo (0.0);

      // DON'T GO UP!!!!
      // if( currentPosition > 0.0 ) {
      //   ArmGo (-0.4);
      // }
      // else {
      //   ArmGo (0.0);
      // }



    }







    //   if (currentPosition > MUR) {
    //     ArmGo (0.1); 
    //     CollectorGo(0.0);
    //   }
    //   else if (currentPosition > MLR) { 
    //     ArmGo (0.1); 
    //     CollectorGo(collectorPower);
    //   }
    //   else {
    //     ArmGo (0.0);
    //     CollectorGo(collectorPower);
    //   }
    // }
    //else {

    //   if (currentPosition < MLR) {
    //     ArmGo (-0.2); 
    //     CollectorGo (collectorPower);
    //   }
    //   else if (currentPosition < MUR) { 
    //     ArmGo (-0.1); 
    //     CollectorGo (0.0);
    //   }
    //   else {
    //     ArmGo (0.0);
    //     CollectorGo (0.0);
    //   }

   //  ArmGo (0.0);
   // }

  }


  public void ArmGo(double power) {
    m_armMotor.set(power);
  }

  public void CollectorGo(double power) {
    m_collectorMotor.set(power);
  }

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

  public void ArmEncoderSetZer0() {
    m_armEncoder.setPosition (0);
  }

  public void SetDeplopyFlag( boolean deplopy) { 
    m_theOneTrueFlag = deplopy;
  }
  public boolean GetDeplopyFlag() { 
    return m_theOneTrueFlag;
  }
  public void SetGoHomeyFlag ( boolean goHomey) {
    m_goHomeFlag = goHomey;
  }
  public boolean GetGoHomeyFlag () {
    return m_goHomeFlag;
  }

  public void ClearFlags() {
    m_goHomeFlag = false;
    m_theOneTrueFlag = false;
  }


}
