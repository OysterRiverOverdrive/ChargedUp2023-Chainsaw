package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArduinoSubsystem extends SubsystemBase {

  private enum PATTERN {
    OFF,
    PURPLE,
    YELLOW
  }

  private Timer timer;
  private Timer colorChange;
  private PATTERN state;
  private I2C arduino;
  private final int ARDUINO_ADDRESS = 4;
  private final Port PORT = Port.kOnboard;
  private final int WRITE_ADDRESS = 0x10;

  /** Creates a new Arduino controller. */
  public ArduinoSubsystem() {
    arduino = new I2C(PORT, ARDUINO_ADDRESS);
    this.state = PATTERN.PURPLE;
    timer = new Timer();
    colorChange = new Timer();

    timer.start();
    colorChange.start();
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
      arduino.write(WRITE_ADDRESS, enumToByte(state));
      timer.reset();
    }
  }

  /** These byte values should match with values in arduino/onrobotlights.ino */
  private byte enumToByte(PATTERN p) {
    if (p == PATTERN.OFF) {
      return 0x40;
    } else if (p == PATTERN.YELLOW) {
      return 0x41;
    } else if (p == PATTERN.PURPLE) {
      return 0x42;
    }
    // If unknown state, return a 0 byte.
    return 0x00;
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
