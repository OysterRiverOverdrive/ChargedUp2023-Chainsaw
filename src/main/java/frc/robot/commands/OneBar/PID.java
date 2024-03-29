// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.OneBar;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.OnebarSubsystem;

public class PID extends CommandBase {
  private final OnebarSubsystem onebarsubsystem;
  private final PIDController PIDo = new PIDController(Constants.kP, Constants.kI, Constants.kD);
  double setPoint;
  /** Creates a new PID. */
  public PID(OnebarSubsystem subsystem) {
    onebarsubsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    setPoint = onebarsubsystem.getEncoder();
    PIDo.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double PIDPos = onebarsubsystem.getEncoder();

    double speedOut = PIDo.calculate(PIDPos, setPoint);
    onebarsubsystem.setMotorSpeed(speedOut);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
