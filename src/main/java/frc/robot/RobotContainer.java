// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.AutoCmd;
import frc.robot.commands.TeleopCmd;
import frc.robot.subsystems.DrivetrainSubsystem;

public class RobotContainer {
  // Define subsystems
  private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();

  // Define Commands
  private final TeleopCmd teleopCmd = new TeleopCmd(drivetrain);
  private final AutoCmd m_autoCommand = new AutoCmd();

  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    drivetrain.setDefaultCommand(teleopCmd);
  }

  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
