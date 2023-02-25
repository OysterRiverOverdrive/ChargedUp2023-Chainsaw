// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.security.spec.EncodedKeySpec;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.RelativeEncoder;

public class WristSubsystem extends SubsystemBase {
  private final CANSparkMax rot = new CANSparkMax(4, MotorType.kBrushed); 
  private final CANSparkMax m_raise = new CANSparkMax(1, MotorType.kBrushed);
  private final Joystick controller = new Joystick(0);
  private final DutyCycleEncoder encWrisDutyCycleEncoderot = new DutyCycleEncoder(0);
  private final DutyCycleEncoder encWrisDutyCycleEncoderaise = new DutyCycleEncoder(1);
  // private RelativeEncoder encoderot = rot.getEncoder();
  // private RelativeEncoder encoderaise = rot.getEncoder();


  public void startup() {

    encWrisDutyCycleEncoderaise.reset();
    encWrisDutyCycleEncoderot.reset();

    // encoderot.setPosition(0);
    // encoderaise.setPosition(0);

  }


   public void encWrisrotright() {

    double rotation = encWrisDutyCycleEncoderot.get();
    double degrees = encWrisDutyCycleEncoderot.get()*360;
    System.out.println(rotation);
    // double rotation = encoderot.getPosition();
    // double degree = encoderot.getPosition();

    if(rotation <= 5) {
    
      rot.set(0.33);

    }

    else{

      rot.stopMotor();

    }

  }


  public void encWrisrotleft() {

    double rotation = encWrisDutyCycleEncoderot.get();
    double degrees = encWrisDutyCycleEncoderot.get()*360;
    System.out.println(degrees);
    // double rotation = encoderot.getPosition();
    // double degree = encoderot.getPosition();

    if(rotation >= -5) {
    
      rot.set(-0.33);

    }

    else{

      rot.stopMotor();

    }

  }

  public void encWrisDutyCycleEncoderaise() {

    double raise = encWrisDutyCycleEncoderaise.get();
    double degrees = encWrisDutyCycleEncoderot.get()*360;
    System.out.println(degrees);

    // double rotation = encoderaise.getPosition();
    // double degree = encoderaise.getPosition();

    if(raise >= -0.3) {
    
      m_raise.set(0.22);

    }

    else{

      m_raise.stopMotor();

    }

  }

    public void encWrisDutyCycleEncoderlower() {

    double lower = encWrisDutyCycleEncoderaise.get();
    double degrees = encWrisDutyCycleEncoderaise.get()*360;
    System.out.println(degrees);

    // double rotation = encoderaise.getPosition();
    // double degree = encoderaise.getPosition();

    if(lower <= 0.3) {
    
      m_raise.set(-0.22);

    }

    else{

      m_raise.stopMotor();

    }

  }

  // public void encWrisrotright90() {

  //   encWrisDutyCycleEncoderot.reset();  
  //   double rotation = encWrisDutyCycleEncoderot.get();
  //   double degrees = encWrisDutyCycleEncoderot.get() * 360;
  //   System.out.println(degrees);

  //   if (degrees <= 90 && degrees >= 0) {

  //     rot.set(0.2);

  //   } else {

  //     m_raise.stopMotor();
  //   }
  // }
  

 public void rotWrist90Left()
 {
   
  rot.set(-0.2);
  
 }

 public void rotWristRight90() {

  rot.set(0.2);

 }


  // public void encWrisrotleft90() {

  //   double rotation = encWrisDutyCycleEncoderot.get();
  //   double degrees = encWrisDutyCycleEncoderot.get() * 360;
  //   System.out.println(rotation);

  //   if (degrees >= -90 && degrees <=0) {

  //     rot.set(-0.2);

  //   } else {

  //     rot.stopMotor();
  //   }
  // }

  public double getrotations () {

    return encWrisDutyCycleEncoderot.get();

  }

  public void resetrot (){

    encWrisDutyCycleEncoderot.reset();

  }

  public void resetraise (){

    encWrisDutyCycleEncoderaise.reset();

  }

  public void stoprot () {

    rot.stopMotor();
    
  }

  public void stopraise () {

  m_raise.stopMotor();

}

public void resetencoder (){

  encWrisDutyCycleEncoderot.reset();

}



  public WristSubsystem() {
    rot.setIdleMode(IdleMode.kBrake);
    m_raise.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void periodic() {
   
    SmartDashboard.putNumber("rot360", encWrisDutyCycleEncoderot.get()*360);
    SmartDashboard.putNumber("rot", encWrisDutyCycleEncoderot.get());

  }
}
