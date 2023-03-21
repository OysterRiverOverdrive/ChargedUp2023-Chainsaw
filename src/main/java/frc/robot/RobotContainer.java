// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.*;
import java.util.function.BooleanSupplier;
import frc.robot.commands.*;
import frc.robot.commands.Claw.*;
import frc.robot.commands.Drive.*;
import frc.robot.commands.OneBar.*;
import frc.robot.commands.Presets.*;
import frc.robot.commands.Wrist.*;
import frc.robot.subsystems.*;

public class RobotContainer {

  // Auto Dropdown
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final String mob = "auto1";
  private final String farmob = "auto2";
  private final String charge = "auto3";
  private final String longauto = "auto4";

  // Defining Controllers
  private final Joystick driver1 = new Joystick(Controllers.DRIVER_ONE_PORT);
  private final Joystick driver2 = new Joystick(Controllers.DRIVER_SEC_PORT);
  private final Joystick operator = new Joystick(Controllers.OPER_PORT);

  // Defining Subsystems
  private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
  private final ControllerSubsystem controls = new ControllerSubsystem();
  private final OnebarSubsystem onebar = new OnebarSubsystem();
  private final LimelightSubsystem limelightSubsystem = new LimelightSubsystem();
  private final WristSubsystem wristSubsystem = new WristSubsystem();
  private final GripperSubsystem gripperSubsystem = new GripperSubsystem();

  // Defining Commands
  // Drivetrain
  private final DriveCmd farmobCmd = new DriveCmd(drivetrain, 140.0, 0.6);
  private final DriveCmd mobCmd = new DriveCmd(drivetrain, 40.0, 0.6);
  private final BalanceSeqCmd chargeCmd = new BalanceSeqCmd(drivetrain);
  private final LongRunAuto longautoCmd = new LongRunAuto(drivetrain);
  private final TeleopCmd teleopCmd = new TeleopCmd(drivetrain);
  private final ShiftdownCmd shiftdown = new ShiftdownCmd(drivetrain);
  private final ShiftupCmd shiftup = new ShiftupCmd(drivetrain);
  private final MoveToAprilTagCmd moveToAprilTagCmd =
      new MoveToAprilTagCmd(drivetrain, limelightSubsystem);

  // One Bar
  private final OnebarDown armDown = new OnebarDown(onebar);
  private final OnebarUp armUp = new OnebarUp(onebar);
  private final OnebarOut armOut = new OnebarOut(onebar);
  private final OnebarIn armIn = new OnebarIn(onebar);
  private final ArmExtStop armExtStop = new ArmExtStop(onebar);
  private final PID stayHeight = new PID(onebar, Constants.KnownValPID);
  private final ArmToHigh armToHigh = new ArmToHigh(onebar, wristSubsystem);
  private final ArmToMid armToMid = new ArmToMid(onebar, wristSubsystem);
  private final BalanceMode balanceMode = new BalanceMode(onebar, wristSubsystem);
  private final SpeedMode speedMode = new SpeedMode(onebar, wristSubsystem);
  private final GroundPickUp groundPickUp = new GroundPickUp(onebar, wristSubsystem);
  private final SubstationPickUp substationPickUp = new SubstationPickUp(onebar, wristSubsystem);

  // // Wrist
  private final LowerCmd lowerCmd = new LowerCmd(wristSubsystem);
  private final RaiseCmd raiseCmd = new RaiseCmd(wristSubsystem);
  private final StopRaiseCmd stopRaiseCmd = new StopRaiseCmd(wristSubsystem);

  // Gripper
  private final InGripperCmd inGripperCmd = new InGripperCmd(gripperSubsystem);
  private final OutGripperCmd outGripperCmd = new OutGripperCmd(gripperSubsystem);
  private final StopGripperCmd stopGripperCmd = new StopGripperCmd(gripperSubsystem);

  // Display
  private final ArduinoSubsystem arduino = new ArduinoSubsystem(SerialPort.Port.kUSB2);

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

  public boolean getPOVbutton(int degree, joysticks joystick) {
    double point;
    if (joystick == joysticks.DRIVER) {
      point = driver1.getPOV();
      if (point == degree) {
        return true;
      } else {
        return false;
      }
    } else {
      point = operator.getPOV();
      if (point == degree) {
        return true;
      } else {
        return false;
      }
    }
  }

  public Trigger POVsupplier(int angle, joysticks joystick) {
    if (joystick == joysticks.DRIVER) {
      BooleanSupplier bsup = () -> getPOVbutton(angle, joystick);
      Trigger mybutton = new Trigger(bsup);
      return mybutton;
    } else {
      BooleanSupplier bsup = () -> getPOVbutton(angle, joystick);
      Trigger mybutton = new Trigger(bsup);
      return mybutton;
    }
  }
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    m_chooser.setDefaultOption("Basic Mobility", mob);
    m_chooser.addOption("Mobility Far", farmob);
    m_chooser.addOption("Charge", charge);
    m_chooser.addOption("Mobility and Charge", longauto);
    SmartDashboard.putData("Auto Run", m_chooser);

    // Configure the button bindings
    configureButtonBindings();
    drivetrain.setDefaultCommand(teleopCmd);
    drivetrain.zeroyawnavx();
    wristSubsystem.resetraise();
    // clawSubsystem.zeroclaw();
    controls.setup();
    onebar.resetEnc();
  }

  private void configureButtonBindings() {

    // Wrist Raise
    supplier(Controllers.logi_lb, joysticks.OPERATOR).onTrue(raiseCmd).onFalse(stopRaiseCmd);
    // Wrist Lower
    supplier(Controllers.logi_rb, joysticks.OPERATOR).onTrue(lowerCmd).onFalse(stopRaiseCmd);

    // Arm Extension In
    POVsupplier(0, joysticks.OPERATOR).onTrue(armIn).onFalse(armExtStop);
    // Arm Extension Out
    POVsupplier(180, joysticks.OPERATOR).onTrue(armOut).onFalse(armExtStop);
    // Arm Rotation Up
    POVsupplier(90, joysticks.OPERATOR).onTrue(armUp).onFalse(stayHeight);
    // Arm Rotation Down
    POVsupplier(270, joysticks.OPERATOR).onTrue(armDown).onFalse(stayHeight);

    // Shift Up
    supplier(Controllers.xbox_rbutton, joysticks.DRIVER).onTrue(shiftup);
    // Shift Down
    supplier(Controllers.xbox_lbutton, joysticks.DRIVER).onTrue(shiftdown);
    // April tag control
    supplier(Controllers.xbox_x, joysticks.DRIVER).onTrue(moveToAprilTagCmd);
    // Balance Seq
    supplier(Controllers.xbox_b, joysticks.DRIVER).onTrue(chargeCmd);

    // Set Arm To Middle Height
    supplier(Controllers.logi_b, joysticks.OPERATOR).onTrue(armToMid);
    // Set Arm To High Height
    supplier(Controllers.logi_y, joysticks.OPERATOR).onTrue(armToHigh);
    // Speed Mode
    supplier(Controllers.logi_x, joysticks.OPERATOR).onTrue(speedMode);
    // Balance Mode
    supplier(Controllers.logi_a, joysticks.OPERATOR).onTrue(balanceMode);
    // Ground Pick Up
    supplier(Controllers.xbox_a, joysticks.DRIVER).onTrue(groundPickUp);
    // Substation Pick Up
    supplier(Controllers.xbox_y, joysticks.DRIVER).onTrue(substationPickUp);

    // gripper out
    supplier(Controllers.logi_lt, joysticks.OPERATOR).onTrue(outGripperCmd).onFalse(stopGripperCmd);
  }

  public Command getAutonomousCommand() {
    switch (m_chooser.getSelected()) {
      case farmob:
        return farmobCmd;
      case charge:
        return chargeCmd;
      case longauto:
        return longautoCmd;
      case mob:
      default:
        return mobCmd;
    }
  }
}
