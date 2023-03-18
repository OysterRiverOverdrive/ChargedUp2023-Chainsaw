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

void loop() {
  if (Serial.available() > 0) {
    byte value = Serial.read();
    if (value == 0x40) {
      Serial.println("turning leds off");
      off();
    } else if (value == 0x41) {
      Serial.println("setting leds yellow");
      yellow();
    } else if (value == 0x42) {
      Serial.println("setting leds purple");
      purple();
    }
  }
  delay(20);

  FastLED.show();
}

void bluewash() {
 for (int i = 0; i < section_one; i++) {
    leds[i] = CRGB::Blue;
  }
}  

void purple() {
 for (int i = 10; i < section_two; i++) {
   leds[i] = CRGB::Purple;
  }
}

void off() {
  for (int i = 20; i < section_three; i++) {
    leds[i] = CRGB::Black;
  }
}

void yellow() {
  for (int i = 30; i < section_four; i++) {
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
