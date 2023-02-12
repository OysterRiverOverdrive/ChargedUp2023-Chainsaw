package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// install new drivetrain library
// manage vendor libraries -> install new library
// https://software-metadata.revrobotics.com/REVLib-2023.json
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
  
  public DrivetrainSubsystem() {
    m_robotDrive.setSafetyEnabled(false);
    leftSide.setInverted(true);
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
    // boolean speeding = false;
    // if (Controllers.CURRENT_SPEEDLIMIT == Controllers.HIGHSPEED) {speeding = false;} 
    // else if (Controllers.CURRENT_SPEEDLIMIT == Controllers.LOWSPEED) {speeding = true;}
    // SmartDashboard.putBoolean("Lowering Speed", speeding);
    // SmartDashboard.putNumber("Speedlimit", Controllers.CURRENT_SPEEDLIMIT);
    // SmartDashboard.putBoolean("Safety", m_robotDrive.isSafetyEnabled());
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
