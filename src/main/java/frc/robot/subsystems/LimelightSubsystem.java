// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// import frc.robot.Constants.VisionConstants;

public class LimelightSubsystem extends SubsystemBase {

  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry tx; // = table.getEntry("tx");
  private NetworkTableEntry ty; // = table.getEntry("ty");

  private NetworkTableEntry pipeln; // = table.getEntry("getpipe");
  NetworkTableEntry tv = table.getEntry("tv");

  private double y; // angle from camera to target

  private double tgtViz;

  /** Creates a new LimelightSubSys. */
  public LimelightSubsystem() {

    tx = table.getEntry("tx");
    ty = table.getEntry("ty");

    pipeln = table.getEntry("getpipe");
  }

  @Override
  public void periodic() {

    tv = table.getEntry("tv");
    double tgtSeen = tv.getDouble(0.0);
    if (tgtSeen == 1.0) {
      SmartDashboard.putBoolean("Target Seen", true);
      SmartDashboard.putNumber("target Id", table.getEntry("tid").getDouble(0.0));
      SmartDashboard.putNumber("pipe line", pipeln.getDouble(0.0));
      SmartDashboard.putNumber("distance constantly updated", getDistance());
    }
    // TODO get tgt ID  - based of pipeline
    else {
      SmartDashboard.putBoolean("Target Seen", false);
      SmartDashboard.putNumber("target Id", -99);
      SmartDashboard.putNumber("pipe line", -99);
    }
  }

  public double getDistance() {
    double tgtHeightInches = 18.00;
    double cameraHeightInches = 10.5;
    double offSetInches = tgtHeightInches - cameraHeightInches;
    double cameraMountAngleDegrees = -1.0;
    double tgtAngleRadians = 0.0;

    tgtViz = tv.getDouble(0.0);

    double dist = -10000.00;
    if (tgtViz == 1) // if limelight can see target
    {
      y = ty.getDouble(0.0);

      tgtAngleRadians = Units.degreesToRadians(cameraMountAngleDegrees + y);
      dist = offSetInches / (Math.tan(tgtAngleRadians));
    }
    SmartDashboard.putNumber("April tag dist inches ", dist);
    return dist;
  }

  public double getTargetx() {
    double retVal = -10000.00;
    tgtViz = tv.getDouble(0.0);

    if (tgtViz == 1) // if limelight can see target
    {
      retVal = tx.getDouble(0.0);
    }
    return retVal;
  }

  public double getTargety() {

    double retVal = -10000.00;
    tgtViz = tv.getDouble(0.0);
    System.out.println("tgt viz " + tgtViz);

    if (tgtViz == 1) // if limelight can see target
    {
      retVal = ty.getDouble(0.0);
    }
    return retVal;
  }
}
