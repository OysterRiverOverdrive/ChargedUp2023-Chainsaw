// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class OnebarSubsystem extends SubsystemBase {
  /** Creates a new OnebarSubsystem. */
  private final CANSparkMax rotMotor = new CANSparkMax(Constants.OnebarRot, MotorType.kBrushless);

  private final CANSparkMax extMotor = new CANSparkMax(Constants.OnebarExt, MotorType.kBrushless);
  private RelativeEncoder encRelativeEncoder = rotMotor.getEncoder();
  private final AnalogPotentiometer pot = new AnalogPotentiometer(Constants.potOneBarPort);

  public OnebarSubsystem() {
    // armInUse = false;
    extMotor.setInverted(true);
  }

  public void armOut() {
    double pValue = pot.get();
    double percentage = pValue * 100.0;
    SmartDashboard.putNumber("Arm Extension %", percentage);
    if (percentage < Constants.potMaxPerc) {
      extMotor.set(Constants.onebarExtSpeed);
    } else {
      extMotor.stopMotor();
    }
  }

  public void armIn() {
    double pValue = pot.get();
    double percentage = pValue * 100.0;
    SmartDashboard.putNumber("Arm Extension %", percentage);
    if (percentage > Constants.potMinPerc) {
      extMotor.set(Constants.onebarExtSpeed * -1);
    } else {
      extMotor.stopMotor();
    }
  }

  public void armUp() {
    rotMotor.set(Constants.onebarRotSpeed * -1); // constraints were moved into the command
  }

  public void armDown() {
    rotMotor.set(Constants.onebarRotSpeed); // constraints were moved into the command
  }

  public void armRotationStop() {
    rotMotor.stopMotor();
  }

  public void armExtensionStop() {
    extMotor.stopMotor();
  }

  public void resetEnc() {
    encRelativeEncoder.setPosition(0);
  }

  public double getEncoder() {
    return encRelativeEncoder.getPosition();
  }

  public double getPot() {
    return pot.get();
  }

  public void setMotorSpeed(double speed) {
    rotMotor.set(speed);
  }

  @Override
  public void periodic() {

    double eValue = getEncoder();
    SmartDashboard.putNumber("Encoder Value", eValue);
  }
}
