package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrainSubsystem;

/** An example command that uses an example subsystem. */
public class teleopCmd extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final drivetrainSubsystem drivesubsystem;

  private final SlewRateLimiter slrForTurn =
      new SlewRateLimiter(Constants.SLEWTURN); // from 2 to 2.5 to 3.5 to 4.0 to 4.5
  private final SlewRateLimiter slrForDrive = new SlewRateLimiter(Constants.SLEWSPEED);

  private final Joystick driver = new Joystick(Constants.DRIVER_PORT);
  private boolean isTeleOp = false;

  public teleopCmd(drivetrainSubsystem subsystem) {
    drivesubsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double turn =
        slrForTurn.calculate(
            driver.getRawAxis(Constants.DRIVER_TURN) * Constants.SPEEDLIMIT_TURN * -1.0);
    // double speed = slrForDrive.calculate (m_stick.getRawAxis(1)*-0.85);
    double speed =
        slrForDrive.calculate(
            driver.getRawAxis(Constants.DRIVER_SPEED)
                * Constants.SPEEDLIMIT_SPEED); // todo new value .95

    drivesubsystem.teleop(speed, turn);
  }

  public void setTeleOpMode(boolean teleOPMode) {
    isTeleOp = teleOPMode;
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
