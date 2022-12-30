package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// install new drivetrain library
// manage vendor libraries -> install new library
// https://software-metadata.revrobotics.com/REVLib.json


public class drivetrainSubsystem extends SubsystemBase {

    private final PWMSparkMax left1 = new PWMSparkMax(Constants.LeftDrive1);
    private final PWMSparkMax left2 = new PWMSparkMax(Constants.LeftDrive2);

    MotorControllerGroup leftSide = new MotorControllerGroup(left1, left2);

    private final PWMSparkMax right1 = new PWMSparkMax(Constants.RightDrive1);
    private final PWMSparkMax right2 = new PWMSparkMax(Constants.RightDrive2);

    MotorControllerGroup rightSide = new MotorControllerGroup(right1, right2);

    private final DifferentialDrive m_robotDrive = new DifferentialDrive(leftSide, rightSide);

    public drivetrainSubsystem() {
        leftSide.setInverted(true);
    }

    public void teleop(double speed, double turn) {
    if (Constants.arcadedriver == true) {
      m_robotDrive.arcadeDrive(speed,turn);
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
