// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

public class MoveToAprilTagCmd extends CommandBase {
  DrivetrainSubsystem drv;
  LimelightSubsystem camera;

  double standOffDistInches = 31.00; //  distance we want to stop from target

  boolean cmdFinished = false;
  double speed = 0.0;
  final double speedKp = -0.01;
  final double turnRateKp = 0.015;

  /** Creates a new ToAprilTagCmd. */
  public MoveToAprilTagCmd(DrivetrainSubsystem drivetrain, LimelightSubsystem limelight) {

    // Use addRequirements() here to declare subsystem dependencies.
    camera = limelight;
    drv = drivetrain; // pipeline choose is null

    addRequirements(drivetrain);
    addRequirements(limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    cmdFinished = false;

    speed = -0.6;

    if (camera.pLine == 5) {
      standOffDistInches = 60.0;
    } else {
      standOffDistInches = 45.0;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (cmdFinished == false) {
      double distToMyTgt = camera.getDistance();

      if (distToMyTgt != -10000 && distToMyTgt < 50) // dist is inches
      {
        // speed proportions if distance less than 50 in
        speed = distToMyTgt * speedKp; // calculating speed to help make the sto smoother
      }

      if (distToMyTgt != -10000.00 && distToMyTgt <= standOffDistInches) // the bot is at its goal
      {

        speed = 0.0;
        drv.arcadeDrive(0, 0); // stop the bot once reached the distance

        cmdFinished = true;
      }

      if (cmdFinished == false && distToMyTgt != -10000.00) {
        driveToTarget(speed);
      }
    }
  }

  public void driveToTarget( // method to drive to target
      double speed) {

    double myx = camera.getTargetx();

    double turnRate = myx * turnRateKp;

    drv.arcadeDrive(speed, turnRate);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return cmdFinished;
  }
}
