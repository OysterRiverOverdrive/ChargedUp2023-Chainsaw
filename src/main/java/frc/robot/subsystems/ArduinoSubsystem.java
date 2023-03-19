package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArduinoSubsystem extends SubsystemBase {

  private enum PATTERN {
    OFF,
    PURPLE,
    YELLOW
  }

  private SerialPort.Port port;
  private SerialPort handler;
  private Timer timer;
  private Timer colorChange;
  private PATTERN state;
  private boolean initialized = false;

  /** Creates a new Arduino controller. */
  public ArduinoSubsystem(SerialPort.Port port) {
    this.port = port;
    this.state = PATTERN.PURPLE;
    timer = new Timer();
    colorChange = new Timer();

    timer.start();
    colorChange.start();
  }

  private void connect() {
    System.out.println("ArduinoSubsystem: connecting on port "+this.port);
    try {
      this.handler = new SerialPort(9600, this.port);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    this.initialized = true;
  }

  /** Call this function constantly sends the desired state to the arduino. */
  @Override
  public void periodic() {
    if (colorChange.get() > 4) {
      if (this.state == PATTERN.OFF) {
        this.state = PATTERN.PURPLE;
      } else if (this.state == PATTERN.PURPLE) {
        this.state = PATTERN.YELLOW;
      } else if (this.state == PATTERN.YELLOW) {
        this.state = PATTERN.OFF;
      }
      colorChange.reset();
    }

    // Send the current state on a timer.
    if (timer.get() > 1) {

      if (!this.initialized) {
        connect();
        return;
      }
  
      System.out.println("Sending lighting state: " + state + " to " + handler);
      int n = handler.write(enumToByte(state), 1);
      if (n == 0) {
        System.out.println("ARDUINO: closing connection to arduino.");
        this.handler.close();
        this.initialized = false;
        return;
      }

      if (handler.getBytesReceived() > 0) {
        System.out.print(handler.readString());
      }
      timer.reset();
    }
  }

  /** These byte values should match with values in arduino/onrobotlights.ino */
  private byte[] enumToByte(PATTERN p) {
    if (p == PATTERN.OFF) {
      return new byte[] {0x40};
    } else if (p == PATTERN.YELLOW) {
      return new byte[] {0x41};
    } else if (p == PATTERN.PURPLE) {
      return new byte[] {0x42};
    }
    // If unknown state, return a 0 byte.
    return new byte[] {0x00};
  }

  public void ledOff() {
    state = PATTERN.OFF;
  }

  public void ledPurple() {
    state = PATTERN.PURPLE;
  }

  public void ledYellow() {
    state = PATTERN.YELLOW;
  }
}
