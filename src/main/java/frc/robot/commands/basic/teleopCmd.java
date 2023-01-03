package frc.robot.commands.basic;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrainSubsystem;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class teleopCmd extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final drivetrainSubsystem drivesubsystem;
  private double turns;
  private double speeds;
  private final Joystick driver1 = new Joystick(Constants.DRIVER_ONE_PORT);
  private final Joystick driver2 = new Joystick(Constants.DRIVER_SEC_PORT);
  private final SlewRateLimiter slrForTurn = new SlewRateLimiter(Constants.SLEWTURN);
  private final SlewRateLimiter slrForDrive = new SlewRateLimiter(Constants.SLEWSPEED);

  private boolean isTeleOp = false;

  public teleopCmd(drivetrainSubsystem subsystem) {
    drivesubsystem = subsystem;
    // turns = turnvariable;
    // speeds = speedvariable;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (Constants.DRIVER_W_BUTTONS == false) {
      turns = driver1.getRawAxis(Constants.DRIVER_TURN);
      speeds = driver2.getRawAxis(Constants.DRIVER_SPEED);
    }
  }

  // Called every time the scheduler runs while the command is scheduled. 
// stick.getRawAxis(Constants.DRIVER_TURN)
// stick.getRawAxis(Constants.DRIVER_SPEED)
  @Override
  public void execute() {
    double turn = slrForTurn.calculate(turns*Constants.SPEEDLIMIT_TURN);
    //double speed = slrForDrive.calculate (m_stick.getRawAxis(1)*-0.85);
    double speed = slrForDrive.calculate (speeds*Constants.SPEEDLIMIT_SPEED);//todo new value .95

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
