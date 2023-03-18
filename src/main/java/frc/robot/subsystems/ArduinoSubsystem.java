package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

public class ArduinoSubsystem {

  private enum PATTERN {
    INITIALIZING,
    PURPLE,
    YELLOW
  }

  private SerialPort.Port port;
  private SerialPort handler;
  private Timer timer;
  private PATTERN state;

  /** Creates a new Arduino controller. */
  public ArduinoSubsystem(SerialPort.Port port) {
    this.port = port;
    this.handler = new SerialPort(9600, port);
    this.state = PATTERN.INITIALIZING;
    timer = new Timer();
    timer.start();
  }

  /**
   * Call this function from robotPeriodic so that the desired state is sent to the arduino
   * continuously.
   */
  public void periodic() {
    // Send the current state on a timer.
    if (timer.get() > 1) {
      System.out.println("Sending lighting state: " + state);
      int n = handler.write(enumToByte(state), 1);
      System.out.printf("Wrote %d bytes to the arduino\n", n);

      if (handler.getBytesReceived() > 0) {
        System.out.print(handler.readString());
      }
    }
  }

  private byte[] enumToByte(PATTERN p) {
    if (p == PATTERN.INITIALIZING) {
      return new byte[] {0x40};
    } else if (p == PATTERN.YELLOW) {
      return new byte[] {0x41};
    } else if (p == PATTERN.PURPLE) {
      return new byte[] {0x42};
    }
    // If unknown state, return a 0 byte.
    return new byte[] {0x00};
  }
}
