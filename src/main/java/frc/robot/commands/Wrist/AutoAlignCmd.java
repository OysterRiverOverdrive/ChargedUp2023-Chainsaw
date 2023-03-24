// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Wrist;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj.Timer;

public class AutoAlignCmd extends CommandBase {

  /** Creates a new LowerCmd. */
  private WristSubsystem wrist;

  private final PIDController PIDo = new PIDController(.18, .05, 0);
  private double rotation;
  Timer timer = new Timer();

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

    double speedOut = PIDo.calculate(wrist.getraise(), rotation);
    wrist.autoAlign(speedOut);
    if (Math.abs(speedOut) < 0.02) {
      timer.start();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean status = false;
    if (timer.get() >= 0.3) {
      status = true;
    }
    return status;
  }
}
