// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.commands.*;
import frc.robot.commands.Claw.*;
import frc.robot.commands.OneBar.*;
import frc.robot.commands.Wrist.*;
import frc.robot.subsystems.*;
import java.util.function.BooleanSupplier;
import edu.wpi.first.math.controller.PIDController;

public class RobotContainer {

  // Auto Dropdown
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final String mob = "auto1";
  private final String farmob = "auto2";
  private final String charge = "auto3";
  private final String mobandcharge = "auto4";

  // Defining Controllers
  private final Joystick driver1 = new Joystick(Controllers.DRIVER_ONE_PORT);
  private final Joystick driver2 = new Joystick(Controllers.DRIVER_SEC_PORT);
  private final Joystick operator = new Joystick(Controllers.OPER_PORT);

  // Defining Subsystems
  private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
  private final ControllerSubsystem controls = new ControllerSubsystem();
  private final OnebarSubsystem onebar = new OnebarSubsystem();
  private final PIDController pidController = new PIDController(Constants.kP, Constants.kI, Constants.kD);
  private final LimelightSubsystem limelightSubsystem = new LimelightSubsystem();
  private final WristSubsystem wristSubsystem = new WristSubsystem();
  private final ClawSubsystem clawSubsystem = new ClawSubsystem();

  // Defining Commands
  // Drivetrain
  private final AutoCmd mobandchargeCmd = new AutoCmd(drivetrain);
  private final DriveCmd farmobCmd = new DriveCmd(drivetrain, 80.0);
  private final DriveCmd mobCmd = new DriveCmd(drivetrain, 40.0);
  private final BalanceSeqCmd chargeCmd = new BalanceSeqCmd(drivetrain);
  private final TeleopCmd teleopCmd = new TeleopCmd(drivetrain);
  private final ShiftdownCmd shiftdown = new ShiftdownCmd(drivetrain);
  private final ShiftupCmd shiftup = new ShiftupCmd(drivetrain);
  private final MoveToAprilTagCmd moveToAprilTagCmd =
      new MoveToAprilTagCmd(drivetrain, limelightSubsystem);

  // OneBar
  private final OnebarDown armDown = new OnebarDown(onebar);
  private final OnebarUp armUp = new OnebarUp(onebar);
  private final OnebarOut armOut = new OnebarOut(onebar);
  private final OnebarIn armIn = new OnebarIn(onebar);
  private final ArmExtStop armExtStop = new ArmExtStop(onebar);
  private final ArmRotStop armRotStop = new ArmRotStop(onebar);
  private final PID stayHeight = new PID(onebar,pidController);

  // Wrist
  private final LowerCmd lowerCmd = new LowerCmd(wristSubsystem);
  private final RaiseCmd raiseCmd = new RaiseCmd(wristSubsystem);
  private final RotLeftCmd rotLeftCmd = new RotLeftCmd(wristSubsystem);
  private final RotRightCmd rotRightCmd = new RotRightCmd(wristSubsystem);
  private final StopRaiseCmd stopRaiseCmd = new StopRaiseCmd(wristSubsystem);
  private final StopRotCmd stopRotCmd = new StopRotCmd(wristSubsystem);

  // Claw
  private final ClampCmd clampCmd = new ClampCmd(clawSubsystem);
  private final ReleaseCmd releaseCmd = new ReleaseCmd(clawSubsystem);
  private final ShiftLeftCmd shiftLeftCmd = new ShiftLeftCmd(clawSubsystem);
  private final ShiftRightCmd shiftRightCmd = new ShiftRightCmd(clawSubsystem);
  private final StopClawCmd stopClawCmd = new StopClawCmd(clawSubsystem);
  private final RotLeft90Cmd rotLeft90Cmd = new RotLeft90Cmd(wristSubsystem);
  private final RotRight90Cmd rotRight90Cmd = new RotRight90Cmd(wristSubsystem);

  public void setbrake() {
    drivetrain.setBrake();
  }

  public void setcoast() {
    drivetrain.setCoast();
  }

  private enum joysticks {
    DRIVER,
    OPERATOR
  }

  public Trigger supplier(int buttonID, joysticks joystick) {
    if (joystick == joysticks.DRIVER) {
      BooleanSupplier bsup = () -> driver1.getRawButton(buttonID);
      Trigger mybutton = new Trigger(bsup);
      return mybutton;
    } else {
      BooleanSupplier bsup = () -> operator.getRawButton(buttonID);
      Trigger mybutton = new Trigger(bsup);
      return mybutton;
    }
  }
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    m_chooser.setDefaultOption("Basic Mobility", mob);
    m_chooser.addOption("Mobility Far", farmob);
    m_chooser.addOption("Charge", charge);
    m_chooser.addOption("Mobility & Charge", mobandcharge);
    SmartDashboard.putData("Auto Run", m_chooser);

    // Configure the button bindings
    configureButtonBindings();
    drivetrain.setDefaultCommand(teleopCmd);
    drivetrain.zeroyawnavx();
    controls.setup();
  }

  private void configureButtonBindings() {

    // Wrist Raise
    supplier(1, joysticks.DRIVER).onTrue(raiseCmd).onFalse(stopRaiseCmd);
    // Wrist Lower
    supplier(2, joysticks.DRIVER).onTrue(lowerCmd).onFalse(stopRaiseCmd);
    // Wrist Left
    supplier(3, joysticks.DRIVER).onTrue(rotLeftCmd).onFalse(stopRotCmd);
    // Wrist Right
    supplier(4, joysticks.DRIVER).onTrue(rotRightCmd).onFalse(stopRotCmd);
    // Wrist Left 90
    supplier(5, joysticks.DRIVER).onTrue(rotLeft90Cmd);
    // Wrist Right 90
    supplier(6, joysticks.DRIVER).onTrue(rotRight90Cmd);

    // Arm Extension In
    supplier(5, joysticks.DRIVER).onTrue(armIn).onFalse(armExtStop);
    // Arm Extension Out
    supplier(3, joysticks.DRIVER).onTrue(armOut).onFalse(armExtStop);
    // Arm Rotation Up
    supplier(6, joysticks.DRIVER).onTrue(armUp).onFalse(stayHeight);
    // Arm Rotation Down
    supplier(4, joysticks.DRIVER).onTrue(armDown).onFalse(stayHeight);

    // Shift Up
    supplier(Controllers.xbox_rbutton, joysticks.DRIVER).onTrue(shiftup);
    // Shift Down
    supplier(Controllers.xbox_lbutton, joysticks.DRIVER).onTrue(shiftdown);

    supplier(Controllers.xbox_lbutton, joysticks.DRIVER).onTrue(shiftdown);
    // April tag control
    supplier(Controllers.xbox_b, joysticks.DRIVER).onTrue(moveToAprilTagCmd);

    // Close Claw
    supplier(5, joysticks.DRIVER).onTrue(clampCmd).onFalse(stopClawCmd);
    // Open Claw
    supplier(6, joysticks.DRIVER).onTrue(releaseCmd).onFalse(stopClawCmd);
    // Shift Claw Left
    supplier(7, joysticks.DRIVER).onTrue(shiftLeftCmd).onFalse(stopClawCmd);
    // Shift Claw Right
    supplier(8, joysticks.DRIVER).onTrue(shiftRightCmd).onFalse(stopClawCmd);
  }

  public Command getAutonomousCommand() {
    switch (m_chooser.getSelected()) {
      case farmob:
        return farmobCmd;
      case charge:
        return chargeCmd;
      case mobandcharge:
        return mobandchargeCmd;
      case mob:
      default:
        return mobCmd;
    }
  }
}
