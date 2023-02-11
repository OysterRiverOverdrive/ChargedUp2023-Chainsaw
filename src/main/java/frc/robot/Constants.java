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
  public static int LeftDrive1 = 6;
  public static int LeftDrive2 = 7;
  public static int RightDrive1 = 1;
  public static int RightDrive2 = 4;
  // establishing motor callouts and their CAN-IDs

  public static double FORSPEED = 0.38;
  public static double REVSPEED = Constants.FORSPEED * -1;
  public static double SLEWTURN = 4.5;
  public static double SLEWSPEED = 2.2;
  public static double SPEEDLIMIT_TURN = 0.75;
  public static double SPEEDLIMIT_SPEED = 0.95;

  // Basic Controller configuraton (Xbox Pro)
  public static int DRIVER_PORT = 0;
  public static int DRIVER_TURN = 4;
  public static int DRIVER_SPEED = 1;

  // Potentiometer and Encoder maximums, minimums, and ratios
  public static double ratio = 0.01;
  public static double potMaxPerc = 96;
  public static double potMinPerc = 10;
  public static double encMaxVal = 1;
  public static double encMinVal = -1;

  // Ports for Encoder, Potentiometer, and Motors
  public static int encOneBarPort = 0;
  public static int potOneBarPort = 0;
  public static int motorRotID = 4;
  public static int motorExtID = 3;
}
