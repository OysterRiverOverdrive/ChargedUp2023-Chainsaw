package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
// install new drivetrain library
// manage vendor libraries -> install new library
// https://software-metadata.revrobotics.com/REVLib-2023.json
import edu.wpi.first.wpilibj.SerialPort;
// install new drivetrain library
// manage vendor libraries -> install new library
// https://dev.studica.com/releases/2023/NavX.json
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Controllers;

public class DrivetrainSubsystem extends SubsystemBase {

  private final CANSparkMax left1 = new CANSparkMax(Constants.LeftDrive1, MotorType.kBrushless);
  private final CANSparkMax left2 = new CANSparkMax(Constants.LeftDrive2, MotorType.kBrushless);

  MotorControllerGroup leftSide = new MotorControllerGroup(left1, left2);

  private final CANSparkMax right1 = new CANSparkMax(Constants.RightDrive1, MotorType.kBrushless);
  private final CANSparkMax right2 = new CANSparkMax(Constants.RightDrive2, MotorType.kBrushless);

  MotorControllerGroup rightSide = new MotorControllerGroup(right1, right2);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(leftSide, rightSide);

  private AHRS navx = new AHRS(SerialPort.Port.kUSB1);

  // left side encoder
  private RelativeEncoder m_encoder_Left = left1.getEncoder();
  // right side encoder
  private RelativeEncoder m_encoder_Right = right1.getEncoder();

  private double angles;
  private double degrees;

  public DrivetrainSubsystem() {
    m_robotDrive.setSafetyEnabled(false);
    leftSide.setInverted(true);
  }

  public void setBrake() {
    right1.setIdleMode(IdleMode.kBrake);
    right2.setIdleMode(IdleMode.kBrake);
    left1.setIdleMode(IdleMode.kBrake);
    left2.setIdleMode(IdleMode.kBrake);
  }

  public void setCoast() {
    right1.setIdleMode(IdleMode.kCoast);
    right2.setIdleMode(IdleMode.kCoast);
    left1.setIdleMode(IdleMode.kCoast);
    left2.setIdleMode(IdleMode.kCoast);
  }

  public void zeroencoders() {
    m_encoder_Right.setPosition(0);
    m_encoder_Left.setPosition(0);
  }

  public void zeroyawnavx() {
    navx.calibrate();
  }

  public boolean move(double inches) {
    // Inches divided by 2*3(wheel radius)*pi
    inches = inches / 18.8495559215;
    if (m_encoder_Left.getPosition() / 9.52 <= inches) {
      m_robotDrive.arcadeDrive(-0.6, 0);
      return false;
    } else {
      m_robotDrive.arcadeDrive(0, 0);
      return true;
    }
  }

  public void setdegree(double degreeed) {
    degrees = (navx.getYaw() - (navx.getYaw() - degreeed) + 540) % 360 - 180;
  }

  public boolean turn() {
    // this turns the sensors raw data(-180,180)
    double angle = 0;
    double raw_angle = navx.getYaw();
    angle = raw_angle;
    angles = angle;

    if (angle + 2 > degrees && angle - 2 < degrees) {
      m_robotDrive.arcadeDrive(0, 0);
      return true;
    } else if (degrees < angle) {
      m_robotDrive.arcadeDrive(0, -0.2);
      return false;
    } else if (degrees > angle) {
      m_robotDrive.arcadeDrive(0, 0.2);
      return false;
    } else {
      return false;
    }

    // System.out.println(angle);
  }

  public boolean balancemvmnt() {
    double angle = navx.getRoll();
    double errorvalue = 9.20;
    double speed = 0;
    boolean dashboardf;
    boolean dashboardb;
    boolean stopped;

    if (angle > errorvalue) {
      dashboardf = true;
      speed = -0.32;
    } else {
      dashboardf = false;
    }

    if (angle < errorvalue * -1.0) {
      dashboardb = true;
      speed = 0.32;
    } else {
      dashboardb = false;
    }

    if (dashboardb == false && dashboardf == false) {
      right1.stopMotor();
      right2.stopMotor();
      left1.stopMotor();
      left2.stopMotor();
      stopped = true;
    } else {
      m_robotDrive.arcadeDrive(speed, 0);
      stopped = false;
    }
    SmartDashboard.putBoolean("Drive forward", dashboardf);
    SmartDashboard.putBoolean("Drive Backwards", dashboardb);
    return stopped;
  }

  public void speedup() {
    Controllers.CURRENT_SPEEDLIMIT = Controllers.Gears.HIGH;
  }

  public void speeddown() {
    Controllers.CURRENT_SPEEDLIMIT = Controllers.Gears.LOW;
  }

  public void teleop(double speed, double turn) {
    if (Controllers.arcadedriver == true) {
      m_robotDrive.arcadeDrive(speed, turn);
    } else {
      m_robotDrive.tankDrive(speed, turn);
    }
  }

  // making only arcade drive method for vision processing system to be able to input speed and turn
  // for going to april tag
  public void arcadeDrive(double speed, double turn) {
    m_robotDrive.arcadeDrive(speed, turn);
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
