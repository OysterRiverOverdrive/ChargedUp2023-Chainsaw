// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase {
  /** Creates a new ClawSubsystem. */
  public ClawSubsystem() {}

  private final CANSparkMax motleft = new CANSparkMax(5, MotorType.kBrushless);
  private final CANSparkMax motright = new CANSparkMax(1, MotorType.kBrushless);
  private final DutyCycleEncoder leftenc = new DutyCycleEncoder(0);
  private final DutyCycleEncoder rightenc = new DutyCycleEncoder(1);

  MotorControllerGroup clawGroup = new MotorControllerGroup(motleft, motright);

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
    clawGroup.set(-0.4);
  }

  public void stop() {

    clawGroup.stopMotor();
  }

  @Override
  public void periodic() {
    // shiftright();
    // shiftleft();
    // clamp();
    // release();
    // stop();
  }
}
