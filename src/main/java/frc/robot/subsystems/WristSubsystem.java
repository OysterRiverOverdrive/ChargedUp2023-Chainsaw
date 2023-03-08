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
  private final CANSparkMax rot = new CANSparkMax(24, MotorType.kBrushless); // not used so useless can id was assigned, will show red in riolog
  private final CANSparkMax m_raise = new CANSparkMax(Constants.Wrist, MotorType.kBrushless);
  // private final Joystick controller = new Joystick(0);
  private RelativeEncoder encoderot = rot.getEncoder();
  private RelativeEncoder encoderaise = m_raise.getEncoder();
  // private final DutyCycleEncoder encWrisDutyCycleEncoderot = new DutyCycleEncoder(0);
  // private final DutyCycleEncoder encWrisDutyCycleEncoderaise = new DutyCycleEncoder(1);

  public void startup() {

    // encWrisDutyCycleEncoderaise.reset();
    // encWrisDutyCycleEncoderot.reset();

    encoderot.setPosition(0);
    encoderaise.setPosition(0);
  }

  public void encWrisrotright() {

    // double rotation = encWrisDutyCycleEncoderot.get();
    // double degrees = encWrisDutyCycleEncoderot.get()*360;
    // System.out.println(rotation);
    double rotation = encoderot.getPosition();
    double degree = encoderot.getPosition();

    if (rotation <= 5) {

      rot.set(0.33);

    } else {

      rot.stopMotor();
    }
  }

  public void encWrisrotleft() {

    // double rotation = encWrisDutyCycleEncoderot.get();
    // double degrees = encWrisDutyCycleEncoderot.get()*360;
    // System.out.println(degrees);
    double rotation = encoderot.getPosition();
    double degree = encoderot.getPosition();

    if (rotation >= -5) {

      rot.set(-0.33);

    } else {

      rot.stopMotor();
    }
  }

  public void encWrisDutyCycleEncoderaise() {

    // double raise = encWrisDutyCycleEncoderaise.get();
    // double degrees = encWrisDutyCycleEncoderot.get()*360;
    // System.out.println(degrees);

    double rotation = encoderaise.getPosition() / 40;
    double degree = encoderaise.getPosition();

    if (rotation >= -0.5) {

      m_raise.set(0.22);

    } else {

      m_raise.stopMotor();
    }
  }

  public void encWrisDutyCycleEncoderlower() {

    // double lower = encWrisDutyCycleEncoderaise.get();
    // double degrees = encWrisDutyCycleEncoderaise.get()*360;
    // System.out.println(degrees);

    double rotation = encoderaise.getPosition() / 40;
    double degree = encoderaise.getPosition();

    if (rotation <= 0.5) {

      m_raise.set(-0.22);

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

  public double getrotations() {

    // return encWrisDutyCycleEncoderot.get();
    return encoderot.getPosition();
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

  public WristSubsystem() {
    rot.setIdleMode(IdleMode.kBrake);
    m_raise.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void periodic() {

    SmartDashboard.putNumber("rot360", encoderot.getPosition() * 360);
    SmartDashboard.putNumber("rot", encoderot.getPosition());
  }
}
