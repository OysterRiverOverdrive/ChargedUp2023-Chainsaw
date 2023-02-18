// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WristSubsystem extends SubsystemBase {
  private final CANSparkMax rot = new CANSparkMax(1, MotorType.kBrushed);
  private final CANSparkMax m_raise = new CANSparkMax(4, MotorType.kBrushed);
  private final Joystick controller = new Joystick(0);
  private final DutyCycleEncoder encWrisDutyCycleEncoderot = new DutyCycleEncoder(0);
  private final DutyCycleEncoder encWrisDutyCycleEncoderaise = new DutyCycleEncoder(1);
  /**
   * Creates a new WristSubsystem.
   *
   * @return
   */
  public void startup() {

    encWrisDutyCycleEncoderaise.reset();
    encWrisDutyCycleEncoderot.reset();
  }

  public void encWrisrotright() {

    double rotation = encWrisDutyCycleEncoderot.get();
    double degrees = encWrisDutyCycleEncoderot.get() * 360;
    System.out.println(degrees);

    if (rotation <= 5) {

      rot.set(0.33);

    } else {

      rot.stopMotor();
    }
  }

  public void encWrisrotleft() {

    double rotation = encWrisDutyCycleEncoderot.get();
    double degrees = encWrisDutyCycleEncoderot.get() * 360;

    System.out.println(degrees);

    if (rotation >= -5) {

      rot.set(-0.33);

    } else {

      rot.stopMotor();
    }
  }

  public void encWrisDutyCycleEncoderaise() {

    double raise = encWrisDutyCycleEncoderaise.get();
    double degrees = encWrisDutyCycleEncoderot.get() * 360;
    System.out.println(degrees);

    if (raise >= -0.3) {

      m_raise.set(0.22);

    } else {

      m_raise.stopMotor();
    }
  }

  public void encWrisDutyCycleEncoderlower() {

    double lower = encWrisDutyCycleEncoderaise.get();
    double degrees = encWrisDutyCycleEncoderaise.get() * 360;
    System.out.println(degrees);

    if (lower <= 0.3) {

      m_raise.set(-0.22);

    } else {

      m_raise.stopMotor();
    }
  }

  public void encWrisrotright90() {

    double rotation = encWrisDutyCycleEncoderot.get();
    double degrees = encWrisDutyCycleEncoderot.get() * 360;
    System.out.println(degrees);

    if (degrees <= 90) {

      rot.set(0.33);

    } else {

      m_raise.stopMotor();
    }
  }

  public void encWrisrotleft90() {

    double rotation = encWrisDutyCycleEncoderot.get();
    double degrees = encWrisDutyCycleEncoderot.get() * 360;
    System.out.println(degrees);

    if (degrees >= -90) {

      rot.set(-0.5);

    } else {

      rot.stopMotor();
    }
  }

  public void resetrot() {

    encWrisDutyCycleEncoderot.reset();
  }

  public void resetraise() {

    encWrisDutyCycleEncoderaise.reset();
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

    SmartDashboard.putNumber("rot", encWrisDutyCycleEncoderot.get());
    SmartDashboard.putNumber("raise", encWrisDutyCycleEncoderaise.get());
  }
}
