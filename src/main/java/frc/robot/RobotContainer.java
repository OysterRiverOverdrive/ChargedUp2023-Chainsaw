// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.basic.autoCmd;
import frc.robot.commands.basic.teleopCmd;
import frc.robot.subsystems.controllerSubsystem;
import frc.robot.subsystems.drivetrainSubsystem;

public class RobotContainer {
  // Defining Controllers
  private final Joystick driver1 = new Joystick(Controllers.DRIVER_ONE_PORT);
  private final Joystick driver2 = new Joystick(Controllers.DRIVER_SEC_PORT);
  private final Joystick operator = new Joystick(Controllers.OPER_PORT);

  // Defining Subsystems
  private final drivetrainSubsystem drivetrain = new drivetrainSubsystem();
  private final controllerSubsystem controls = new controllerSubsystem();

  // Defining Commands
  private final autoCmd m_autoCommand = new autoCmd();
  private final teleopCmd teleopCmd = new teleopCmd(drivetrain);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    drivetrain.setDefaultCommand(teleopCmd);
    controls.setup();
  }

  private void configureButtonBindings() {}

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
