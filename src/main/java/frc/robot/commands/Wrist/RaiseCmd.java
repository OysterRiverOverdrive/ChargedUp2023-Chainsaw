// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Wrist;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WristSubsystem;

public class RaiseCmd extends CommandBase {
  /** Creates a new RaiseCmd. */
  private WristSubsystem wrist;

  public RaiseCmd(WristSubsystem wrists) {
    wrist = wrists;
    addRequirements(wrists);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Raising Wrist");
    wrist.encWrisDutyCycleEncoderaise();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean status = false;
    return status;
  }
}
