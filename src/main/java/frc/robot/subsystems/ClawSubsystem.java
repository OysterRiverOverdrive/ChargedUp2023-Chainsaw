// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawSubsystem extends SubsystemBase {
  /** Creates a new ClawSubsystem. */
  public ClawSubsystem() {}

  private final CANSparkMax motleft = new CANSparkMax(Constants.Clawleft, MotorType.kBrushless);
  private final CANSparkMax motright = new CANSparkMax(Constants.Clawright, MotorType.kBrushless);
  private final RelativeEncoder leftenc = motleft.getEncoder();
  private final RelativeEncoder rightenc = motright.getEncoder();

  MotorControllerGroup clawGroup = new MotorControllerGroup(motleft, motright);

  public void zeroclaw() {
    // Claw needs to be zeroed open, if not the claw can misbehave
    leftenc.setPosition(0);
    rightenc.setPosition(0);
  }

  public void clawbrake() {
    motleft.setIdleMode(IdleMode.kBrake);
    motleft.setIdleMode(IdleMode.kBrake);
  }

  public double getleftenc() {
    return leftenc.getPosition();
  }

  public double getrightenc() {
    return rightenc.getPosition();
  }

  public void shiftright() {

    motleft.setInverted(false);
    motright.setInverted(false);
    clawGroup.set(0.4);
  }

  public void shiftleft() {

    motleft.setInverted(false);
    motright.setInverted(false);
    clawGroup.set(-0.4);
  }

  public void clamp() {

    motleft.setInverted(false);
    motright.setInverted(true);
    clawGroup.set(0.4);
  }

  public void release() {
    motleft.setInverted(true);
    motright.setInverted(false);
    // if (getrightenc() >= 0) {
    //   motright.set(-0.4);
    // } else {
    //   motright.stopMotor();
    // }
    motright.set(-0.4);
    // if (getleftenc() <= 0) {
    //   motleft.set(-0.4);
    // } else {
    //   motleft.stopMotor();
    // }
    motleft.set(-0.4);
  }

  public void stop() {

    clawGroup.stopMotor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Claw Enc Right", getrightenc());
    SmartDashboard.putNumber("Claw Enc Left", getleftenc());
  }
}
