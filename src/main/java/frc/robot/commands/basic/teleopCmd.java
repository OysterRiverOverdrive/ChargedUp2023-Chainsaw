package frc.robot.commands.basic;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Controllers;
import frc.robot.subsystems.DrivetrainSubsystem;

/** An example command that uses an example subsystem. */
public class TeleopCmd extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DrivetrainSubsystem drivesubsystem;

  private double turns;
  private double speeds;
  private final Joystick driver1 = new Joystick(Controllers.DRIVER_ONE_PORT);
  private final Joystick driver2 = new Joystick(Controllers.DRIVER_SEC_PORT);
  private final SlewRateLimiter slrForTurn = new SlewRateLimiter(Constants.SLEWTURN);
  private final SlewRateLimiter slrForDrive = new SlewRateLimiter(Constants.SLEWSPEED);

  private boolean isTeleOp = false;

  public TeleopCmd(DrivetrainSubsystem subsystem) {
    drivesubsystem = subsystem;
    // turns = turnvariable;
    // speeds = speedvariable;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // if (Controllers.DRIVER_W_BUTTONS == false) {
      turns = driver1.getRawAxis(Controllers.DRIVER_TURN);
      speeds = driver2.getRawAxis(Controllers.DRIVER_SPEED);
    // }
  }

  // Called every time the scheduler runs while the command is scheduled.
  // stick.getRawAxis(Constants.DRIVER_TURN)
  // stick.getRawAxis(Constants.DRIVER_SPEED)
  @Override
  public void execute() {
    double turn = slrForTurn.calculate(turns * Constants.SPEEDLIMIT_TURN);
    // double speed = slrForDrive.calculate (m_stick.getRawAxis(1)*-0.85);
    double speed = slrForDrive.calculate(speeds * Constants.SPEEDLIMIT_SPEED); // todo new value .95

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
