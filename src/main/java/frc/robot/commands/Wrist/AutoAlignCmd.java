// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Wrist;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WristSubsystem;

public class AutoAlignCmd extends CommandBase {

  /** Creates a new LowerCmd. */
  private WristSubsystem wrist;

  PIDController PIDo = new PIDController(1.5, 0, 0);
  double rotation;
  Timer timer = new Timer();

  public AutoAlignCmd(WristSubsystem wrists, double rotations) {
    wrist = wrists;
    rotation = rotations;
    addRequirements(wrists);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    PIDo.reset();
    timer.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double PIDval = wrist.getraise();
    double speedOut = PIDo.calculate(PIDval, rotation);
    // System.out.println(speedOut);
    wrist.autoAlign(speedOut);
    if (Math.abs(speedOut) < 0.04) {
      timer.start();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // wrist.autoAlign(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean status = false;
    if (timer.get() >= 0.3) {
      status = true;
    }
    wrist.stoprot(status);
    return status;
  }
}
