// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.basic.autoCmd;
import frc.robot.commands.basic.teleopCmd;
import frc.robot.subsystems.drivetrainSubsystem;

public class RobotContainer {
  // Defining Controllers
  private final Joystick driver1 = new Joystick(Constants.DRIVER_ONE_PORT);
  private final Joystick driver2 = new Joystick(Constants.DRIVER_SEC_PORT);
  private final Joystick operator = new Joystick(Constants.OPER_PORT);

  // Defining Subsystems
  private final drivetrainSubsystem drivetrain = new drivetrainSubsystem();

  // Defining Commands
  private final teleopCmd teleopCmd = new teleopCmd(drivetrain);

  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final autoCmd m_autoCommand = new autoCmd();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
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
