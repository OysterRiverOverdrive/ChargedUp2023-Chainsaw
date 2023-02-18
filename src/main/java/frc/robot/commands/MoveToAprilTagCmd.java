// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.subsystems.TalonDriveTrain;//in case we were to use winch doctor
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

// import frc.robot.Constants.VisionConstants;

public class MoveToAprilTagCmd extends CommandBase {
  DrivetrainSubsystem drv;
  LimelightSubsystem camera;

  double standOffDistInches = 18.00; //  was 18 and mesurment is in inches

  boolean cmdFinished = false;
  double speed = 0.0;
  final double speedKp = 0.01;
  final double turnRateKp = 0.01;

  private final SendableChooser<Integer> pipelineChooser = new SendableChooser<>();

  /** Creates a new ToAprilTagCmd. */
  public MoveToAprilTagCmd(DrivetrainSubsystem drivetrain, LimelightSubsystem limelight) {

    // Use addRequirements() here to declare subsystem dependencies.
    camera = limelight;
    drv = drivetrain; // pipeline choose is null

    addRequirements(drivetrain);
    addRequirements(limelight);
    pipelineChooser.setDefaultOption(
        "Blue Tag 1", 0); // TODO add the other tags that are on the field
    pipelineChooser.setDefaultOption("Blue Tag 2", 1);
    pipelineChooser.setDefaultOption("Blue Tag 8", 7);
    SmartDashboard.putData("Choose Pipeline", pipelineChooser);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    cmdFinished = false;

    speed = 0.6;

    int pLine = pipelineChooser.getSelected().intValue();

    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pLine);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (cmdFinished == false) {
      double distToMyTgt = camera.getDistance();

      // System.out.println(distToMyTgt + "  distance to target");

      if (distToMyTgt != -10000 && distToMyTgt < 50) // dist is inches
      {
        // speed proportions if distance less than 50 in
        speed = distToMyTgt * speedKp; // was og +0.01
      }

      if (distToMyTgt != -10000.00
          && distToMyTgt <= standOffDistInches) // the bot is at its goal//was at 30
      {

        System.out.println("ALL Done");
        speed = 0.0;
        drv.arcadeDrive(0, 0); // stop the bot once reached the distance

        cmdFinished = true;
      }

      if (cmdFinished == false && distToMyTgt != -10000.00) {
        driveToTarget(speed);
      }
    }
  }

  public void driveToTarget(
      double speed) // TODO need to account for if getTargetx() returns -10000.00--> maybe do if ==
        // -10000 stob but dont finish command
      {

    double myx = camera.getTargetx();

    double turnRate = myx * turnRateKp; // og kp was -0.01

    SmartDashboard.putNumber("speed", speed);
    SmartDashboard.putNumber("turn Rate", turnRate);
    drv.arcadeDrive(speed, turnRate);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if (cmdFinished) {
      System.out.println(cmdFinished + "  Cmd finished");
    }

    return cmdFinished;
  }
}
