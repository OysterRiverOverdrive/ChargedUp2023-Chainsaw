// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// import frc.robot.Constants.VisionConstants;

public class LimelightSubsystem extends SubsystemBase {

  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry tx; // = table.getEntry("tx");
  private NetworkTableEntry ty; // = table.getEntry("ty");

  private NetworkTableEntry pipeln; // = table.getEntry("getpipe");

  private final SendableChooser<Integer> pipelineChooser = new SendableChooser<>();

  NetworkTableEntry tv = table.getEntry("tv");

  private double y; // angle from camera to target

  private double tgtViz;

  double tgtHeightInches = 18.00;

  public int pLine = 1;

  /** Creates a new LimelightSubSys. */
  public LimelightSubsystem() {

    tx = table.getEntry("tx");
    ty = table.getEntry("ty");

    pipeln = table.getEntry("getpipe");

    NetworkTableInstance.getDefault()
        .getTable("limelight")
        .getEntry("pipeline")
        .setNumber(0); // added to make pipeline 0
  }

  @Override
  public void periodic() {

    tv = table.getEntry("tv");
    double tgtSeen = tv.getDouble(0.0);
    if (tgtSeen == 1.0) {
      SmartDashboard.putBoolean("Target Seen", true);
      SmartDashboard.putNumber("target Id", table.getEntry("tid").getDouble(0.0));

    }
    // TODO get tgt ID  - based of pipeline
    else {
      SmartDashboard.putBoolean("Target Seen", false);
      SmartDashboard.putNumber("target Id", -99);
    }

    // SmartDashboard.putNumber("april tag
    // id",NetworkTableInstance.getDefault().getTable("limelight").getEntry("tid").getDouble(-10000));

    SmartDashboard.putNumber("distance constantly updated", getDistance());

    pipelineChooser.setDefaultOption(
        "Red Tag 1", 0); // TODO add the other tags that are on the field
    pipelineChooser.setDefaultOption("Red Tag 2", 1);
    pipelineChooser.setDefaultOption("Red Tag 3", 2);
    pipelineChooser.setDefaultOption("Red Loading Zone Tag 5", 5);

    pipelineChooser.setDefaultOption("Blue Tag 6", 4);
    pipelineChooser.setDefaultOption("Blue Tag 7", 6);
    pipelineChooser.setDefaultOption("Blue Tag 8", 7);
    pipelineChooser.setDefaultOption("Blue Loading Zone Tag 4", 5);

    pipelineChooser.setDefaultOption("any april tag", 8);

    pipelineChooser.setDefaultOption("Cone Nodes", 9);

    SmartDashboard.putData("Choose Pipeline", pipelineChooser);

    pLine = pipelineChooser.getSelected().intValue();

    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pLine);

    if (pLine == 5) {
      tgtHeightInches = 27.375; // height of the loading zone targets

    } else {
      if (pLine == 9) {
        tgtHeightInches = 25.5;
      } else {
        tgtHeightInches = 18.0; // height of the cube node targets
      }
    }
  }

  public double getDistance() {

    double cameraHeightInches = 13.5;
    double offSetInches = tgtHeightInches - cameraHeightInches;
    double cameraMountAngleDegrees = 2.31;
    double tgtAngleRadians = 0.0;

    tgtViz = tv.getDouble(0.0);

    double dist = -10000.00;
    if (tgtViz == 1) // if limelight can see target
    {
      y = ty.getDouble(0.0);

      tgtAngleRadians = Units.degreesToRadians(cameraMountAngleDegrees + y);
      dist = offSetInches / (Math.tan(tgtAngleRadians));
    }

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

    if (tgtViz == 1) // if limelight can see target
    {
      retVal = ty.getDouble(0.0);
    }
    return retVal;
  }
}
