// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Wrist;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WristSubsystem;

public class AutoAlignCmd extends CommandBase {

  /** Creates a new LowerCmd. */
  private WristSubsystem wrist;
  private double rotation;

  public AutoAlignCmd(WristSubsystem wrists, double rotations) {
    wrist = wrists;
    rotation = rotations;
    addRequirements(wrists);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    wrist.autoAlign(rotation);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double encoder = wrist.getraise();
    if(encoder == rotation) {
      return true;
    }
    else{
      return false;
    }
  }
}
