// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class OnebarSubsystem extends SubsystemBase {
  /** Creates a new OnebarSubsystem. */
  private final CANSparkMax rotMotor = new CANSparkMax(Constants.motorRotID, MotorType.kBrushless);

  private final CANSparkMax extMotor = new CANSparkMax(Constants.motorExtID, MotorType.kBrushless);
  private final DutyCycleEncoder encBarDutyCycleEncoder =
      new DutyCycleEncoder(Constants.encOneBarPort);
  private final AnalogPotentiometer pot = new AnalogPotentiometer(Constants.potOneBarPort);

  public OnebarSubsystem() {}

  public void setup() {
    resetEnc();
  }

  public void InverseMotor() {
    rotMotor.setInverted(true);
  }

  public void armOut() {
    double pValue = pot.get();
    double percentage = pValue * 100.0;
    if (percentage < Constants.potMaxPerc) {
      extMotor.set(Constants.FORSPEED);
    }
  }

  public void armIn() {
    double pValue = pot.get();
    double percentage = pValue * 100.0;
    if (percentage > Constants.potMinPerc) {
      extMotor.set(Constants.REVSPEED);
    }
  }

  public void armUp() {
    double eValue = encBarDutyCycleEncoder.get();
    eValue = eValue * Constants.ratio;
    if (eValue < Constants.encMaxVal) {
      rotMotor.set(Constants.FORSPEED);
    } else {
      rotMotor.stopMotor();
    }
  }

  public void armDown() {
    double eValue = encBarDutyCycleEncoder.get();
    eValue = eValue * Constants.ratio;
    if (eValue > Constants.encMinVal) {
      rotMotor.set(Constants.REVSPEED);
    } else {
      rotMotor.stopMotor();
    }
  }

  public void armRotationStop() {
    rotMotor.stopMotor();
  }

  public void armExtensionStop() {
    extMotor.stopMotor();
  }

  public void resetEnc() {
    encBarDutyCycleEncoder.reset();
  }

  public double getEncoder() {
    return encBarDutyCycleEncoder.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double eValue = encBarDutyCycleEncoder.get();
    SmartDashboard.putNumber("Encoder Value", eValue);
  }
}
