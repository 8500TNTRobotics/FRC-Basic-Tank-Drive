package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private Joystick joystick; // Naming the Joystick

  private VictorSPX m_rearLeftMotor; //Naming the rear left VictorSPX motor controller
  private VictorSPX m_frontLeftMotor; //Naming the front left VictorSPX motor controller
  private VictorSPX m_rearRightMotor; //Naming the rear right VictorSPX motor controller
  private VictorSPX m_frontRightMotor; //Naming the front right VictorSPX motor controller

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  
  joystick = new Joystick(0); //Choosing the port which our joystick is plugged
  //Port of the joystick can be found via Driver Station

  m_rearLeftMotor = new VictorSPX(1); //Giving the ID of rear left VictorSpx
  m_frontLeftMotor = new VictorSPX(2); //Giving the ID of front left VictorSpx
  m_rearRightMotor = new VictorSPX(3); //Giving the ID of rear right VictorSpx
  m_frontRightMotor = new VictorSPX(4); //Giving the ID of front right VictorSpx
  //IDs` of the VictorSPX can be found and changed via Phoenix Tuner X

  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    
    basicTankDrive();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}

  public void basicTankDrive() {
  
    double rotation = joystick.getRawAxis(4);
    double speed = joystick.getRawAxis(5);

    //Movements

    //Moving while turning right
    if ((rotation>0.09) && ((speed<-0.09) || (speed>0.09))) {
      m_frontLeftMotor.set(VictorSPXControlMode.PercentOutput,-speed);
      m_rearLeftMotor.set(VictorSPXControlMode.PercentOutput,-speed);
      m_frontRightMotor.set(VictorSPXControlMode.PercentOutput,speed/2);
      m_rearRightMotor.set(VictorSPXControlMode.PercentOutput,speed/2);
    }
    //Moving while turning left
    else if ((rotation<-0.09) && ((speed<-0.09) || (speed>0.09))) {
      m_frontLeftMotor.set(VictorSPXControlMode.PercentOutput,-speed/2);
      m_rearLeftMotor.set(VictorSPXControlMode.PercentOutput,-speed/2);
      m_frontRightMotor.set(VictorSPXControlMode.PercentOutput,speed);
      m_rearRightMotor.set(VictorSPXControlMode.PercentOutput,speed);
    } 
    //Rotation on its own axis
    else if ((rotation<-0.09) || (rotation>0.09)) {
      m_frontLeftMotor.set(VictorSPXControlMode.PercentOutput,-rotation/2);
      m_rearLeftMotor.set(VictorSPXControlMode.PercentOutput,-rotation/2);
      m_frontRightMotor.set(VictorSPXControlMode.PercentOutput,-rotation/2);
      m_rearRightMotor.set(VictorSPXControlMode.PercentOutput,-rotation/2);
    } 
    //Moving back and front
    else if ((speed>0.09) || (speed<-0.09)) {
      m_frontLeftMotor.set(VictorSPXControlMode.PercentOutput,-speed);
      m_rearLeftMotor.set(VictorSPXControlMode.PercentOutput,-speed);
      m_frontRightMotor.set(VictorSPXControlMode.PercentOutput,speed);
      m_rearRightMotor.set(VictorSPXControlMode.PercentOutput,speed);
    }
    else {
      m_frontLeftMotor.set(VictorSPXControlMode.PercentOutput,0);
      m_rearLeftMotor.set(VictorSPXControlMode.PercentOutput,0);
      m_frontRightMotor.set(VictorSPXControlMode.PercentOutput,0);
      m_rearRightMotor.set(VictorSPXControlMode.PercentOutput,0);
    }
  }
}