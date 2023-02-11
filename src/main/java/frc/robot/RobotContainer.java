// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ArmExtStop;
import frc.robot.commands.ArmRotStop;
import frc.robot.commands.OnebarDown;
import frc.robot.commands.OnebarUp;
import frc.robot.commands.OnebarIn;
import frc.robot.commands.OnebarOut;
import frc.robot.commands.ArmExtStop;
import frc.robot.commands.ArmRotStop;
import frc.robot.commands.TeleopCmd;
import frc.robot.commands.AutoCmd;
import frc.robot.subsystems.ControllerSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.OnebarSubsystem;

import edu.wpi.first.wpilibj2.command.button.*;
import java.util.function.BooleanSupplier;

public class RobotContainer {
  // Defining Controllers
  private final Joystick driver1 = new Joystick(Controllers.DRIVER_ONE_PORT);
  private final Joystick driver2 = new Joystick(Controllers.DRIVER_SEC_PORT);
  private final Joystick operator = new Joystick(Controllers.OPER_PORT);

  // Defining Subsystems
  private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
  private final ControllerSubsystem controls = new ControllerSubsystem();
  private final OnebarSubsystem onebar = new OnebarSubsystem();

  // Defining Commands
  private final AutoCmd m_autoCommand = new AutoCmd();
  private final TeleopCmd teleopCmd = new TeleopCmd(drivetrain);
  private final OnebarDown armDown = new OnebarDown(onebar);
  private final OnebarUp armUp = new OnebarUp(onebar);
  private final OnebarOut armOut = new OnebarOut(onebar);
  private final OnebarIn armIn = new OnebarIn(onebar);
  private final ArmExtStop armExtStop = new ArmExtStop(onebar);
  private final ArmRotStop armRotStop = new ArmRotStop(onebar);


  public Trigger supplier(int buttonID){
    BooleanSupplier bsup = () -> operator.getRawButton(buttonID);
    Trigger mybutton = new Trigger(bsup);
    return mybutton;
  }
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    
    // Configure the button bindings
    configureButtonBindings();
    drivetrain.setDefaultCommand(teleopCmd);
    controls.setup();
    onebar.setup();
  }

  private void configureButtonBindings() {
    supplier(5).onTrue(armIn).onFalse(armExtStop);
    supplier(3).onTrue(armOut).onFalse(armExtStop);
    supplier(6).onTrue(armUp).onFalse(armRotStop);
    supplier(4).onTrue(armDown).onFalse(armRotStop);
    
  }

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
