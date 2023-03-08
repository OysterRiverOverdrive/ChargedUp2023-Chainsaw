// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static int RightDrive1 = 1;
  public static int RightDrive2 = 2;
  public static int LeftDrive1 = 4;
  public static int LeftDrive2 = 3;
  public static int OnebarRot = 5;
  public static int OnebarExt = 6;
  public static int Wrist = 7;
  public static int Clawright = 8;
  public static int Clawleft = 9;
  // establishing motor callouts and their CAN-IDs

  public static double onebarRotSpeed = 0.5;
  public static double onebarExtSpeed = 0.6;
  public static double SLEWTURN = 4.5;
  public static double SLEWSPEED = 2.2;
  public static double SPEEDLIMIT_TURN = 0.55;
  public static double SPEEDLIMIT_SPEED = 0.95;

  // Basic Controller configuraton (Xbox Pro)
  public static int DRIVER_PORT = 0;
  public static int DRIVER_TURN = 4;
  public static int DRIVER_SPEED = 1;

  // Potentiometer and Encoder maximums
  public static double potMaxPerc = 90;
  public static double potMinPerc = 20;
  public static int potOneBarPort = 0;

  // PID Constants
  public static double kP = 1;
  public static double kI = 1.5;
  public static double kD = 0.2;

  // PID Assisted Arm heights
  public final double[] armHeights = {
    0, 180, 360
  }; // these are, in order, lowest, middle, and highest. There can different placements.
  public static int index =
      1; // this is where the basic selection of the arm height you want it to be. this is for an
  // array.
  // numbers correspond to the values {0, 1, 2...}

}
