package frc.robot.commands.Claw;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.GripperSubsystem;

public class AutoGripperOutCmd extends CommandBase {
  private Timer timer = new Timer();
  /** Creates a new RollerPush. */
  GripperSubsystem gripper;

  public AutoGripperOutCmd(GripperSubsystem grippers) {
    gripper = grippers;
    addRequirements(grippers);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    gripper.autorotate(false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    gripper.stopGripper();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean retVal = false;

    double currTime = timer.get();

    if (currTime >= 0.5) {
      retVal = true;
    } else {
      retVal = false;
    }

    return retVal;
  }
}
