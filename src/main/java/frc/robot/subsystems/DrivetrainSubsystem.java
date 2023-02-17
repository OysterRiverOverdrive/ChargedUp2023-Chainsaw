package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
// install new drivetrain library
// manage vendor libraries -> install new library
// https://software-metadata.revrobotics.com/REVLib-2023.json
import com.revrobotics.CANSparkMax.IdleMode;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
// install new drivetrain library
// manage vendor libraries -> install new library
// https://dev.studica.com/releases/2023/NavX.json

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

  //left side encoder
  private RelativeEncoder m_encoder_Left = left1.getEncoder();
  //right side encoder
  private RelativeEncoder m_encoder_Right = right1.getEncoder();

  
  public DrivetrainSubsystem() {
    m_robotDrive.setSafetyEnabled(false);
    leftSide.setInverted(true);
  }

  public void setbrake() {
    right1.setIdleMode(IdleMode.kBrake);
    right2.setIdleMode(IdleMode.kBrake);
    left1.setIdleMode(IdleMode.kBrake);
    left2.setIdleMode(IdleMode.kBrake);
  }

  public void setCoast() {

  }

  public void zeroencoders() {
    m_encoder_Right.setPosition(0);  
    m_encoder_Left.setPosition(0);
  }



  public void moveforward() {
    if(m_encoder_Right.getPosition()/9.52 >= -1.5f ){
      m_robotDrive.arcadeDrive(0.6, 0);
    }else{
      m_robotDrive.arcadeDrive(0, 0);

    }
  }
  
  public void balancemvmnt(){
    double angle = navx.getRoll();
    double errorvalue = 9.20;
    double speed = 0;
    boolean dashboardf;
    boolean dashboardb;

    if (angle > errorvalue) {
      dashboardf =true;
      speed = 0.35;
    } else {
      dashboardf = false;
    }

    if (angle < errorvalue*-1.0) {
      dashboardb =true;
      speed = -0.35;
    } else {
      dashboardb = false;
    }

    if (dashboardb == false && dashboardf == false) {
    right1.stopMotor();
    right2.stopMotor();
    left1.stopMotor();
    left2.stopMotor();
    } else {
    m_robotDrive.arcadeDrive(speed, 0);
    }
    SmartDashboard.putBoolean("Drive forward", dashboardf);
    SmartDashboard.putBoolean("Drive Backwards", dashboardb);
  }

  public void speedup() {
    Controllers.CURRENT_SPEEDLIMIT = Controllers.HIGHSPEED;
  }

  public void speeddown() {
    Controllers.CURRENT_SPEEDLIMIT = Controllers.LOWSPEED;
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
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
