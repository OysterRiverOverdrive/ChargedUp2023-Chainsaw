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
  private PATTERN state;

  /** Creates a new Arduino controller. */
  public ArduinoSubsystem(SerialPort.Port port) {
    return;
    // this.port = port;
    // this.handler = new SerialPort(9600, this.port);
    // this.state = PATTERN.OFF;
    // timer = new Timer();
    // timer.start();
  }

  /** Call this function constantly sends the desired state to the arduino. */
  @Override
  public void periodic() {
    // Send the current state on a timer.
    if (timer.get() > 1) {
      System.out.println("Sending lighting state: " + state);
      int n = handler.write(enumToByte(state), 1);
      System.out.printf("Wrote %d bytes to the arduino\n", n);

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
