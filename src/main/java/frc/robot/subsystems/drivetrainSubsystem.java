package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// install new drivetrain library
// manage vendor libraries -> install new library
// https://software-metadata.revrobotics.com/REVLib.json
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class drivetrainSubsystem extends SubsystemBase {

  private final CANSparkMax left1 = new CANSparkMax(Constants.LeftDrive1, MotorType.kBrushless);
  private final CANSparkMax left2 = new CANSparkMax(Constants.LeftDrive2, MotorType.kBrushless);

  MotorControllerGroup leftSide = new MotorControllerGroup(left1, left2);

  private final CANSparkMax right1 = new CANSparkMax(Constants.RightDrive1, MotorType.kBrushless);
  private final CANSparkMax right2 = new CANSparkMax(Constants.RightDrive2, MotorType.kBrushless);

  MotorControllerGroup rightSide = new MotorControllerGroup(right1, right2);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(leftSide, rightSide);

  public drivetrainSubsystem() {
    leftSide.setInverted(true);
  }

  public void teleop(double speed, double turn) {
    if (Constants.arcadedriver == true) {
      m_robotDrive.arcadeDrive(speed, turn);
    } else {
      m_robotDrive.tankDrive(speed, turn);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
