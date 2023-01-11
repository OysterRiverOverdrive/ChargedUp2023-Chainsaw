// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Controllers;

public class ControllerSubsystem extends SubsystemBase {
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final String kdefaultdriver = "Default Driver";
  private final String konestickdriver = "One-Stick Driver";
  private final String kxtankdriver = "Xbox Tank Driver";
  private final String kltankdriver = "Logitech Tank Driver";

  /** Creates a new ControllerSubsystem. */
  public ControllerSubsystem() {}

  public void setup() {
    m_chooser.setDefaultOption("Default Driver", kdefaultdriver);
    m_chooser.addOption("One-Stick Driver", konestickdriver);
    m_chooser.addOption("Xbox Tank Driver", kxtankdriver);
    m_chooser.addOption("Logitech Tank Driver", kltankdriver);
    SmartDashboard.putData("Driver Config", m_chooser);
  }

  public void defaultdriveConfig() {
    Controllers.arcadedriver = true;
    Controllers.DRIVER_TURN = Controllers.xbox_rx;
    Controllers.DRIVER_SPEED = Controllers.xbox_ly;
  }

  public void onestickdriveConfig() {
    Controllers.arcadedriver = true;
    Controllers.DRIVER_TURN = Controllers.xbox_lx;
    Controllers.DRIVER_SPEED = Controllers.xbox_ly;
  }

  public void xtankdriveConfig() {
    Controllers.arcadedriver = false;
    Controllers.DRIVER_TURN = Controllers.xbox_ry;
    Controllers.DRIVER_SPEED = Controllers.xbox_ly;
  }

  public void ltankdriveConfig() {
    Controllers.arcadedriver = false;
    Controllers.DRIVER_TURN = Controllers.logi_ry;
    Controllers.DRIVER_SPEED = Controllers.logi_ly;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    switch (m_chooser.getSelected()) {
      case konestickdriver:
        onestickdriveConfig();
        break;
      case kxtankdriver:
        xtankdriveConfig();
        break;
      case kltankdriver:
        ltankdriveConfig();
        break;
      case kdefaultdriver:
      default:
        defaultdriveConfig();
        break;
    }
  }
}
