

package frc.robot;
import edu.wpi.first.math.util.Units;

public final class Constants {


  //** CAN IDs **

  
  //Wiring and Sensors
  public static final int    PDP_CAN_ID       = 1;
  public static final int    PIGEON_CAN_ID    = 2;
    
  //Intake
  public static final int    COLLECTOR_MOTOR_CAN_ID = 4;  //ok
  public static final int    ARM_MOTOR_CAN_ID       = 56; //ok 
  
  //Handler
  public static final int    TRANSPORT_MOTOR_CAN_ID = 10; //ok

  //shooter 
  public static final int    TURRET_MOTOR_CAN_ID    = 6;  //ok
  public static final int    SHOOTER_MOTOR1_CAN_ID  = 11; //ok
  public static final int    SHOOTER_MOTOR2_CAN_ID  = 13; //ok



  //********** DRIVETRAIN CONSTANTS **********

  public static final double DRIVEBASE_WIDTH  =  Units.inchesToMeters(21.2);       //inches, Left to Right
  public static final double DRIVEBASE_LENGTH =  Units.inchesToMeters(21.5);       //inches, Front to Rear

  //-------------------------------------------
  public static final int    FRONTLEFT_DRIVE_CAN_ID   =  23;
  public static final int    FRONTLEFT_TURN_CAN_ID    =  24;
  public static final int    FRONTLEFT_ENCODER_ID     =  1; 
  public static final double FRONTLEFT_ENCODER_OFFSET = (232.5 - 180.0);
  //-------------------------------------------
  public static final int    FRONTRIGHT_DRIVE_CAN_ID   = 33;
  public static final int    FRONTRIGHT_TURN_CAN_ID    = 34;
  public static final int    FRONTRIGHT_ENCODER_ID     = 2;
  public static final double FRONTRIGHT_ENCODER_OFFSET = (130.9 + 180.0);
  //-------------------------------------------
  public static final int    BACKLEFT_DRIVE_CAN_ID     = 32;
  public static final int    BACKLEFT_TURN_CAN_ID      = 31;
  public static final int    BACKLEFT_ENCODER_ID       = 0;
  public static final double BACKLEFT_ENCODER_OFFSET   = (31.2 + 180.0);
  //-------------------------------------------
  public static final int    BACKRIGHT_DRIVE_CAN_ID    = 21;
  public static final int    BACKRIGHT_TURN_CAN_ID     = 22;
  public static final int    BACKRIGHT_ENCODER_ID      = 3;
  public static final double BACKRIGHT_ENCODER_OFFSET  = (294.8-180.0);
  //-------------------------------------------






} 