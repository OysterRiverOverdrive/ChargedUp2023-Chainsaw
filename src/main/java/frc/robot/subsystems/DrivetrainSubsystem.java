package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// install new drivetrain library
// manage vendor libraries -> install new library
// https://software-metadata.revrobotics.com/REVLib-2023.json
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Controllers;
import com.revrobotics.RelativeEncoder;

public class DrivetrainSubsystem extends SubsystemBase {

  private final CANSparkMax left1 = new CANSparkMax(Constants.LeftDrive1, MotorType.kBrushless);
  private final CANSparkMax left2 = new CANSparkMax(Constants.LeftDrive2, MotorType.kBrushless);

  MotorControllerGroup leftSide = new MotorControllerGroup(left1, left2);

  private final CANSparkMax right1 = new CANSparkMax(Constants.RightDrive1, MotorType.kBrushless);
  private final CANSparkMax right2 = new CANSparkMax(Constants.RightDrive2, MotorType.kBrushless);

  //right side encoders
  private RelativeEncoder m_encoder_Left1;
  private RelativeEncoder m_encoder_Left2;

  //right side encoders
  private RelativeEncoder m_encoder_Right1;
  private RelativeEncoder m_encoder_Right2;

  MotorControllerGroup rightSide = new MotorControllerGroup(right1, right2);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(leftSide, rightSide);

  public DrivetrainSubsystem() {
    leftSide.setInverted(true);
  }
  public void robotInit() {
    m_encoder_Right1 = right1.getEncoder();
    m_encoder_Right1.setPosition(0);

    m_encoder_Right2 = right2.getEncoder();
    m_encoder_Right2.setPosition(0);

    m_encoder_Left1 = left1.getEncoder();
    m_encoder_Left1.setPosition(0);

    m_encoder_Left2 = left2.getEncoder();
    m_encoder_Left2.setPosition(0);
  }
  public void teleop(double speed, double turn) {
    if (Controllers.arcadedriver == true) {
      m_robotDrive.arcadeDrive(speed, turn);
    } else {
      m_robotDrive.tankDrive(speed, turn);
    }
  }

  @Override
  public void periodic() {
    System.out.println("left "+m_encoder_Left1.getPosition()+"    left2 "+m_encoder_Left2.getPosition()+"       right1 "+m_encoder_Right1.getPosition()+"    right2 "+m_encoder_Right2.getPosition());
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
