package frc.robot;

public final class Controllers {

  // Default Driver Controller configuraton (Xbox Pro)
  public static int DRIVER_ONE_PORT = 0;
  public static int DRIVER_SEC_PORT = 0;
  public static int DRIVER_TURN = 4;
  public static int DRIVER_SPEED = 1;
  public static int HIGHSPEED = 2;
  public static int LOWSPEED = 1;// 0.5
  public static double LOWEREDSPEED = 0.55;
  public static boolean DRIVER_W_BUTTONS = false;
  public static int CURRENT_SPEEDLIMIT = 2;
  public static boolean arcadedriver = true;

  // Operator Controller Configuration (Logitech Dual Action)
  public static int OPER_PORT = 3;

  // XBOX PRO
  // Joysticks
  public static int xbox_lx = 0;
  public static int xbox_ly = 1;
  public static int xbox_lt = 2;
  public static int xbox_rt = 3;
  public static int xbox_rx = 4;
  public static int xbox_ry = 5;

  // Logitech Dual Action
  // Joysticks
  public static int logi_lx = 0;
  public static int logi_ly = 1;
  public static int logi_rx = 2;
  public static int logi_ry = 3;
}
