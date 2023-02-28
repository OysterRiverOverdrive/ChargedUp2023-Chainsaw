// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;

public class OnebarSubsystem extends SubsystemBase {
  /** Creates a new OnebarSubsystem. */
  private final CANSparkMax rotMotor = new CANSparkMax(Constants.motorRotID, MotorType.kBrushless);
  private final CANSparkMax extMotor = new CANSparkMax(Constants.motorExtID, MotorType.kBrushed);
  // private final DutyCycleEncoder encBarDutyCycleEncoder = new DutyCycleEncoder(Constants.encOneBarPort);
  private RelativeEncoder encRelativeEncoder = rotMotor.getEncoder();
  private final AnalogPotentiometer pot = new AnalogPotentiometer(Constants.potOneBarPort);
  // private final PIDController PID = new PIDController(Constants.kP, Constants.kI, Constants.kD);`
  // public boolean armInUse;
  // private boolean first = true;
  // private boolean last = first;
  // private boolean present;
  
public  OnebarSubsystem()
{
// armInUse = false;
  extMotor.setInverted(true);
}

public void InverseMotor()
{
  rotMotor.setInverted(false);
}


public void armOut(){
  double pValue = pot.get();
  double percentage = pValue * 100.0;
  SmartDashboard.putNumber("Arm Extension %", percentage);
  if(percentage < Constants.potMaxPerc){
    extMotor.set(Constants.REVSPEED);
  }
  else {
  if(percentage > Constants.potMaxPerc){
      extMotor.set(Constants.FORSPEED);
    }
    else{
      extMotor.stopMotor();
    }
  }
}

public void armIn(){
  double pValue = pot.get();
  double percentage = pValue * 100.0;
  SmartDashboard.putNumber("Arm Extension %", percentage);
  if(percentage > Constants.potMinPerc){
    extMotor.set(Constants.FORSPEED);
  }
  else{
    if(percentage < Constants.potMinPerc){
      extMotor.set(Constants.REVSPEED);
    }
    else{
      extMotor.stopMotor();
    }
  }
}

//  public void armUpxx(){
//   armInUse = true;
//   // double eValue = encBarDutyCycleEncoder.get();
//   double eValue = encRelativeEncoder.getPosition();
//   eValue = eValue * Constants.ratio;
//    if (eValue > Constants.encMinVal){ // only works when: enoder > -50
//        rotMotor.set(Constants.FORSPEED);
//      }
//      else{
//        System.out.println("Stopping motor");
//        rotMotor.stopMotor();
//   }
// }

public void armUp()
{
  rotMotor.set(Constants.FORSPEED); // constraints were moved into the command
}




// public void armDown(){
//   armInUse = true;
//   // double eValue = encBarDutyCycleEncoder.get();
//   double eValue = encRelativeEncoder.getPosition();
//   eValue = eValue * Constants.ratio;
//   if (eValue < Constants.encMaxVal){
//       rotMotor.set(Constants.REVSPEED);
//     }
//     else{
//       rotMotor.stopMotor();
//     }
// }

public void armDown(){
  rotMotor.set(Constants.REVSPEED); // constraints were moved into the command
}

public void armRotationStop(){
  rotMotor.stopMotor();
  // armInUse = false;
}

public void armExtensionStop(){
  extMotor.stopMotor();
}

public void resetEnc(){
  // encBarDutyCycleEncoder.reset();
  encRelativeEncoder.setPosition(0);
}

public double getEncoder(){
  // return encBarDutyCycleEncoder.get();
  return encRelativeEncoder.getPosition();
}

public double getPot(){
  return pot.get();
}

public void setMotorSpeed(double speed){
  rotMotor.set(speed);
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // present = armInUse;
    // if(first == true){
    //   last = present;
    //   first = false;
    //   }
    //   else{
    //     if(last == true && present == false){
    //       armSetHeight();
    //     }
    //     last = present;
    //   }

    double eValue = getEncoder();
    SmartDashboard.putNumber("Encoder Value", eValue);
    // System.out.println("encoder value: "  + eValue);
    }
    
  }