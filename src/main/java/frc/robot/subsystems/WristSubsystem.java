// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class WristSubsystem extends SubsystemBase {
  private final CANSparkMax m_raise = new CANSparkMax(Constants.Wrist, MotorType.kBrushless);
  private RelativeEncoder encoderaise = m_raise.getEncoder();

  public void startup() {
    encoderaise.setPosition(0);
  }

  public void encWrisDutyCycleEncoderaise() {
    double rotation = encoderaise.getPosition() / 40;

    if (rotation >= -0.5) {
      m_raise.set(0.22);
    } else {
      m_raise.stopMotor();
    }
  }

  public void encWrisDutyCycleEncoderlower() {
    double rotation = encoderaise.getPosition() / 40;

    if (rotation <= 0.5) {
      m_raise.set(-0.22);
    } else {
      m_raise.stopMotor();
    }
  }

  public void resetraise() {

    encoderaise.setPosition(0);
  }

  public void stopraise() {

    m_raise.stopMotor();
  }

  public WristSubsystem() {
    m_raise.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void periodic() {}
}
