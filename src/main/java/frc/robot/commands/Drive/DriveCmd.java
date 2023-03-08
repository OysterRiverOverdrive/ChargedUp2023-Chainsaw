// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

public class DriveCmd extends CommandBase {
  /** Creates a new DriveCmd. */
  DrivetrainSubsystem drive;

  boolean isdone = false;
  double inches;

  public DriveCmd(DrivetrainSubsystem drives, double inches) {
    drive = drives;
    this.inches = inches;
    addRequirements(drives);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.zeroencoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    isdone = drive.move(inches);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return isdone;
  }
}
