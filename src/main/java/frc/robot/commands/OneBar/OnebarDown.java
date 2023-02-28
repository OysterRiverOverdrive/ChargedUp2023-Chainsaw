// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.OneBar;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.OnebarSubsystem;

public class OnebarDown extends CommandBase {
  private final OnebarSubsystem onebarsubsystem;

  /** Creates a new OneBarDown. */
  public OnebarDown(OnebarSubsystem subsystem) {
    onebarsubsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    onebarsubsystem.armDown();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(onebarsubsystem.getEncoder() < Constants.encMaxVal){
      return false;
    }
    else{
      return true;
    }
  }
}
