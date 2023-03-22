// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.OneBar;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.OnebarSubsystem;
import edu.wpi.first.wpilibj.Timer;

public class PID2 extends CommandBase {
  private final OnebarSubsystem onebarsubsystem;
  private final PIDController PIDo = new PIDController(3, 0,0);

  public double setPoint;
  Timer timer = new Timer();

  /** Creates a new PID. */
  public PID2(OnebarSubsystem subsystem, double pointSet) {
    onebarsubsystem = subsystem;
    setPoint = pointSet;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // System.out.println(setPoint);
    double PIDPos = onebarsubsystem.getEncoder();

    double speedOut = PIDo.calculate(PIDPos, setPoint);
    System.out.println("turn speed: " + speedOut);
    onebarsubsystem.setMotorSpeed(speedOut);
    if (Math.abs(speedOut) < 0.04) {
      timer.reset();
      timer.start();
    } else {
      timer.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean status;
    if (timer.get() > 1) {
      status = true;
    } else {
      status = false;
    }
    System.out.println(status);
    return status;
  }
}
