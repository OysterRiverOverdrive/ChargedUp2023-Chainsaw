// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.OneBar;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.OnebarSubsystem;

public class PID2 extends CommandBase {
  private final OnebarSubsystem onebarsubsystem;
  private final PIDController PIDo = new PIDController(3, 0, 0);

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
    double PIDPos = onebarsubsystem.getEncoder();
    double speedOut = PIDo.calculate(PIDPos, setPoint);
    System.out.println(speedOut);
    onebarsubsystem.setMotorSpeed(speedOut);
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
    if (timer.get() >= 0.2) {
      status = true;
    }
    return status;
  }
}
