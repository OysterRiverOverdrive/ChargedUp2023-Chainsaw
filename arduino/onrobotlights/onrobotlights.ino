// Import the FastLED library so we can use it to control the lights.
#include "FastLED.h"

// Specify which digital IO port the data wire from the LED strip
// will be connected to.
#define LED_PIN  2

// Specify how many LEDs on the strip will be controlled.
#define NUM_LEDS 300
#define section_one 10
#define section_two 20
#define section_three 30
#define section_four 40
#define section_five 50
#define section_six 60

// Create a variable that will store the colors for the LEDs.
CRGB leds[NUM_LEDS];

//I2C address of the arduino
#define I2C_ADDR 4

int pattern = 0;

void setup() {
  // Tell the FastLED library:
  //   * WS2812B - which type of LEDs will be controlled
  //   * LED_PIN - which pin to send out the data signal
  //   * RGB - the color order. It differs by type of LED strip
  //           and could be RGB, RBG, BRG, GBR, ...
  //   * leds - the variable that is storing all the colors
  //   * NUM_LEDS - the number of LEDs to control
  Serial.begin(9600);
  Wire.begin(I2C_ADDR);
  Wire.onReceive(receiveEvent);

  // Ensures that the onboard led LOW == off.
  pinMode(13, OUTPUT);
  digitalWrite(13, LOW);
  
  FastLED.addLeds<WS2812B, LED_PIN, GRB>(leds, NUM_LEDS);

  // clear all pixel data
  FastLED.clear(true);

  // Set the light brightness from 0-255.  If too bright, you
  // can permanently damage the arduino, so use a small number to start.
  FastLED.setBrightness(170);
  
  // The absolute maximum for any single IO pin is 40 mA (this is the maximum. 
  //  You should never actually pull a full 40 mA from a pin. Basically, it's 
  //  the threshold at which Atmel can no longer guarantee the chip won't be 
  //  damaged. You should always ensure you're safely below this current limit.)
  // The total current from all the IO pins together is 200 mA max
  // The 5V output pin is good for ~400 mA on USB, ~900 mA when using an 
  //  external power adapter
  //    The 900 mA is for an adapter that provides ~7V. As the adapter voltage 
  //    increases, the amount of heat the regulator has to deal with also increases, 
  //    so the maximum current will drop as the voltage increases. This is 
  //    called thermal limiting
  // The 3.3V output is capable of supplying 150 mA.
  //    Note - Any power drawn from the 3.3V rail has to go through the 5V rail. 
  //     Therefore, if you have a 100 mA device on the 3.3V output, you need to 
  //     also count it against the 5V total current.
  //
  // Example: limit my draw to 0.2A at 5v of power draw
  //  FastLED.setMaxPowerInVoltsAndMilliamps(5, 200);
  FastLED.setMaxPowerInVoltsAndMilliamps(5, 400);
}

void receiveEvent(int howMany){
  int addr = Wire.read();

  //the robot wants to send some data
  if (addr == 0x10) {
    byte value = Wire.read();

    // These byte values should match with values in
    // ArduinoSubsystem.enumToByte()
    if (value == 0x40) {
      Serial.println("turning leds off");
      pattern = 0;
    } else if (value == 0x41) {
      Serial.println("setting leds yellow");
      pattern = 1;
    } else if (value == 0x42) {
      Serial.println("setting leds purple");
      pattern = 2;
    }
  }
}

void loop() {
  if (pattern == 0) {
    off();
  } else if (pattern == 1) {
    yellow();
  } else if (pattern == 2) {
    purple();
  } else if (pattern == 3) {
    night_rider();
  } else if (pattern == 4) {
    rainbow();
  } else if (pattern == 5) {
    gradient();
  }

  FastLED.show();

  // Roughly match the roborio loop timing.
  delay(20);
}

void bluewash() {
  for (int i = 0; i < section_one; i++) {
    leds[i] = CRGB::Blue;
  }
}

void night_rider() {
  static int pos = 0;
  static int direction = 1;
  static int len = 4;
  static uint8_t hue = 0;
  
  EVERY_N_MILLISECONDS(20) {
    pos += direction;
    if (pos == NUM_LEDS - 1) {
      direction = -1;
    } else if (pos == 0) {
      direction = 1;
    }
    fadeToBlackBy(leds, NUM_LEDS, 50);
    hue++;
    leds[pos] = CHSV(hue, 255, 255);
  }
}

void rainbow() {
  static uint8_t hue = 0;
  for (int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CHSV(hue + i, 255, 255);
  }
  EVERY_N_MILLISECONDS(15) {
    hue++;
  }
}

void gradient() {
  fill_gradient_RGB(leds, NUM_LEDS, CRGB::Purple, CRGB::Teal, CRGB::Teal, CRGB::Purple);
}

void purple() {
 for (int i = 0; i < NUM_LEDS; i++) {
   leds[i] = CRGB::Purple;
  }
}

void off() {
  for (int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CRGB::Black;
  }
}

void yellow() {
  for (int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CRGB::DarkOrange;
  }
}

void five() {
  for (int i = 40; i < section_five; i++) {
    leds[i] = CRGB::OrangeRed;
  }
}

void six() {
  for (int i = 50; i < section_six; i++) {
    leds[i] = CRGB::FairyLight;
  }
}
