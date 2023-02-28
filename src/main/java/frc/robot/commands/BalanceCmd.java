// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

public class BalanceCmd extends CommandBase {
  /** Creates a new BalanceCmd. */
  DrivetrainSubsystem drive;

  boolean motorstop;
  Timer timer = new Timer();

  public BalanceCmd(DrivetrainSubsystem drives) {
    drive = drives;
    addRequirements(drives);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    motorstop = drive.balancemvmnt();
    if (motorstop == true) {
      timer.start();
      timer.reset();
    } else {
      timer.stop();
      timer.reset();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean status;
    if (motorstop == true && timer.get() > 5) {
      status = true;
    } else {
      status = false;
    }
    return status;
  }
}
