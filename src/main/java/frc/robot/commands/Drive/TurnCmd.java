// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DrivetrainSubsystem;

public class TurnCmd extends CommandBase {
  /** Creates a new TurnCmd. */
  DrivetrainSubsystem drive;

  PIDController pid = new PIDController(Constants.kP, Constants.kI, Constants.kD);

  boolean isdone;
  double degree;

  public TurnCmd(DrivetrainSubsystem drives, double degrees) {
    drive = drives;
    degree = degrees;
    addRequirements(drives);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.zeroencoders();
    drive.zeroyawnavx();
    drive.setdegree(degree);
    pid.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    isdone = drive.turn();
    // System.out.println(isdone);
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
