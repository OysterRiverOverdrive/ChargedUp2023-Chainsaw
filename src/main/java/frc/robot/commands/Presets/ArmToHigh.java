// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Presets;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.OneBar.PID2;
import frc.robot.commands.Wrist.AutoAlignCmd;
import frc.robot.subsystems.OnebarSubsystem;
import frc.robot.subsystems.WristSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ArmToHigh extends ParallelCommandGroup {
  /** Creates a new ArmToHigh. */
  public ArmToHigh(OnebarSubsystem onebar, WristSubsystem wrists) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new PID2(onebar, 0.18), // needs testing
        new AutoAlignCmd(wrists, -0.6) // needs testing
        );
  }
}
