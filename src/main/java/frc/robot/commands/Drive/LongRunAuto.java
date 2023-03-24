// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Claw.AutoGripperOutCmd;
import frc.robot.commands.Presets.SubstationPickUp;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.GripperSubsystem;
import frc.robot.subsystems.OnebarSubsystem;
import frc.robot.subsystems.WristSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class LongRunAuto extends SequentialCommandGroup {
  /** Creates a new LongRunAuto. */
  public LongRunAuto(
      DrivetrainSubsystem drive,
      GripperSubsystem grip,
      OnebarSubsystem onebar,
      WristSubsystem wrist) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new SubstationPickUp(onebar, wrist),
        new AutoGripperOutCmd(grip),
        new BringDown(drive, onebar, wrist),
        new BalanceCmd(drive));
  }
}
