// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;

public class ShiftLeftCmd extends CommandBase {
  /** Creates a new ShiftLeftCmd. */
  private ClawSubsystem claw;

  public ShiftLeftCmd(ClawSubsystem claws) {
    claw = claws;
    addRequirements(claws);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    claw.shiftleft();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean retval = false;
    if (claw.getleftenc() <= 0) {
      retval = true;
    }
    return retval;
  }
}
