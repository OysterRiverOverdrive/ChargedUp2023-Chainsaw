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
import frc.robot.commands.Drive.BalanceSeqCmd;
import frc.robot.commands.Drive.DriveCmd;
import frc.robot.commands.Drive.MoveToAprilTagCmd;
import frc.robot.commands.Drive.ShiftdownCmd;
import frc.robot.commands.Drive.ShiftupCmd;
import frc.robot.commands.OneBar.*;
import frc.robot.commands.Wrist.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.GripperSubsystem;
import java.util.function.BooleanSupplier;

public class RobotContainer {

  // Auto Dropdown
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final String mob = "auto1";
  private final String farmob = "auto2";
  private final String charge = "auto3";

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
  private final DriveCmd farmobCmd = new DriveCmd(drivetrain, 140.0);
  private final DriveCmd mobCmd = new DriveCmd(drivetrain, 40.0);
  private final BalanceSeqCmd chargeCmd = new BalanceSeqCmd(drivetrain);
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
  private final ArmRotStop armRotStop = new ArmRotStop(onebar);
  private final PID stayHeight = new PID(onebar);

  // // Wrist
  private final LowerCmd lowerCmd = new LowerCmd(wristSubsystem);
  private final RaiseCmd raiseCmd = new RaiseCmd(wristSubsystem);
  // private final RotLeftCmd rotLeftCmd = new RotLeftCmd(wristSubsystem);
  // private final RotRightCmd rotRightCmd = new RotRightCmd(wristSubsystem);
  private final StopRaiseCmd stopRaiseCmd = new StopRaiseCmd(wristSubsystem);
  // private final StopRotCmd stopRotCmd = new StopRotCmd(wristSubsystem);
  // private final RotLeft90Cmd rotLeft90Cmd = new RotLeft90Cmd(wristSubsystem);
  // private final RotRight90Cmd rotRight90Cmd = new RotRight90Cmd(wristSubsystem);

  // Gripper
  private final InGripperCmd inGripperCmd = new InGripperCmd(gripperSubsystem);
  private final OutGripperCmd outGripperCmd = new OutGripperCmd(gripperSubsystem);
  private final StopGripperCmd stopGripperCmd = new StopGripperCmd(gripperSubsystem);

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
    supplier(Controllers.logi_rb, joysticks.OPERATOR).onTrue(raiseCmd).onFalse(stopRaiseCmd);
    // Wrist Lower
    supplier(Controllers.logi_lb, joysticks.OPERATOR).onTrue(lowerCmd).onFalse(stopRaiseCmd);
    // // Wrist Left
    // supplier(3, joysticks.DRIVER).onTrue(rotLeftCmd).onFalse(stopRotCmd);
    // // Wrist Right
    // supplier(4, joysticks.DRIVER).onTrue(rotRightCmd).onFalse(stopRotCmd);
    // // Wrist Left 90
    // supplier(5, joysticks.DRIVER).onTrue(rotLeft90Cmd);
    // // Wrist Right 90
    // supplier(6, joysticks.DRIVER).onTrue(rotRight90Cmd);

    // Arm Extension In
    supplier(Controllers.logi_x, joysticks.OPERATOR).onTrue(armIn).onFalse(armExtStop);
    // Arm Extension Out
    supplier(Controllers.logi_y, joysticks.OPERATOR).onTrue(armOut).onFalse(armExtStop);
    // Arm Rotation Up
    supplier(Controllers.logi_b, joysticks.OPERATOR).onTrue(armUp).onFalse(stayHeight);
    // Arm Rotation Down
    supplier(Controllers.logi_a, joysticks.OPERATOR).onTrue(armDown).onFalse(stayHeight);

    // Shift Up
    supplier(Controllers.xbox_rbutton, joysticks.DRIVER).onTrue(shiftup);
    // Shift Down
    supplier(Controllers.xbox_lbutton, joysticks.DRIVER).onTrue(shiftdown);
    // April tag control
    supplier(Controllers.xbox_x, joysticks.DRIVER).onTrue(moveToAprilTagCmd);
    // Balance Seq
    supplier(Controllers.xbox_b, joysticks.DRIVER).onTrue(chargeCmd);

    supplier(Controllers.logi_back, joysticks.OPERATOR)
        .onTrue(inGripperCmd)
        .onFalse(stopGripperCmd);

    supplier(Controllers.logi_start, joysticks.OPERATOR)
        .onTrue(outGripperCmd)
        .onFalse(stopGripperCmd);

    // // Close Claw
    // supplier(Controllers.logi_rt, joysticks.OPERATOR).onTrue(clampCmd).onFalse(stopClawCmd);
    // // Open Claw
    // supplier(Controllers.logi_lt, joysticks.OPERATOR).onTrue(releaseCmd).onFalse(stopClawCmd);
    // // Shift Claw Left
    // supplier(Controllers.logi_lbutton, joysticks.OPERATOR)
    //     .onTrue(shiftLeftCmd)
    //     .onFalse(stopClawCmd);
    // // Shift Claw Right
    // supplier(Controllers.logi_rbutton, joysticks.OPERATOR)
    //     .onTrue(shiftRightCmd)
    //     .onFalse(stopClawCmd);
  }

  public Command getAutonomousCommand() {
    switch (m_chooser.getSelected()) {
      case farmob:
        return farmobCmd;
      case charge:
        return chargeCmd;
      case mob:
      default:
        return mobCmd;
    }
  }
}
