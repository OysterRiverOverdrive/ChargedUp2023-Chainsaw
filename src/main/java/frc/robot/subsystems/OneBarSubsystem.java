// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;

public class OnebarSubsystem extends SubsystemBase {
  /** Creates a new OnebarSubsystem. */
  private final CANSparkMax motor = new CANSparkMax(Constants.motorOneBarDeviceID, MotorType.kBrushless);

  public OnebarSubsystem() {}

public void armOut(double percentage){
if(percentage < Constants.potMaxPerc){
  motor.set(0.38);
}
}

public void armIn(double percentage){
  if(percentage > Constants.potMinPerc){
    motor.set(-0.38);
  }
}

public void armUp(double eValue){
  if (eValue < Constants.encMaxVal){
      motor.set(0.38);
    }
    else{
      motor.set(0);
    }
}

public void armDown(double eValue){
  if (eValue > Constants.encMinVal){
      motor.set(-0.38);
    }
    else{
      motor.set(0);
    }
}



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }
}
