// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class WristSubsystem extends SubsystemBase {
  private final CANSparkMax rot = new CANSparkMax(24, MotorType.kBrushless);
  // not used so useless can id was assigned, will show red in riolog
  private final CANSparkMax m_raise = new CANSparkMax(Constants.Wrist, MotorType.kBrushless);
  private RelativeEncoder encoderot = rot.getEncoder();
  private RelativeEncoder encoderaise = m_raise.getEncoder();

  public void startup() {
    encoderot.setPosition(0);
    encoderaise.setPosition(0);
  }

  public void encWrisrotright() {
    double rotation = encoderot.getPosition();
    double degree = encoderot.getPosition();

    if (rotation <= 5) {

      rot.set(0.33);

    } else {

      rot.stopMotor();
    }
  }

  public void encWrisrotleft() {
    double rotation = encoderot.getPosition();
    double degree = encoderot.getPosition();

    if (rotation >= -5) {

      rot.set(-0.33);

    } else {

      rot.stopMotor();
    }
  }

  public void encWrisDutyCycleEncoderaise() {
    // 1 to 40 gear ratio
    double rotation = encoderaise.getPosition() / 360;
    double degree = encoderaise.getPosition();

    if (rotation >= -1) {

      m_raise.set(0.72);

    } else {

      m_raise.stopMotor();
    }
  }

  public void encWrisDutyCycleEncoderlower() {
    // 1 to 40 gear ratio
    double rotation = encoderaise.getPosition() / 360;
    double degree = encoderaise.getPosition();

    if (rotation <= 1) {

      m_raise.set(-0.72);

    } else {

      m_raise.stopMotor();
    }
  }

  public void rotWrist90Left() {

    rot.set(-0.2);
  }

  public void rotWristRight90() {

    rot.set(0.2);
  }

  public double getraise() {
    return encoderaise.getPosition() / 360;
  }

  public void resetrot() {

    encoderot.setPosition(0);
  }

  public void resetraise() {

    encoderaise.setPosition(0);
  }

  public void stoprot() {

    rot.stopMotor();
  }

  public void stopraise() {

    m_raise.stopMotor();
  }

  public void autoAlign(double degrees) {

    double position = encoderaise.getPosition();

    if (degrees < position) {
      m_raise.set(-0.6);
    } else {
      m_raise.set(0.6);
    }
  }

  public WristSubsystem() {
    rot.setIdleMode(IdleMode.kBrake);
    m_raise.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("rot", encoderot.getPosition());
  }
}
