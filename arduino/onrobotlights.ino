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

int g_PowerLimit = 100;  //no go 12000

void setup() {
  // Tell the FastLED library:
  //   * WS2812B - which type of LEDs will be controlled
  //   * LED_PIN - which pin to send out the data signal
  //   * RGB - the color order. It differs by type of LED strip
  //           and could be RGB, RBG, BRG, GBR, ...
  //   * leds - the variable that is storing all the colors
  //   * NUM_LEDS - the number of LEDs to control
 Serial.begin(115200);
 Serial.print(" reached led show ");
  
  FastLED.addLeds<WS2812B, LED_PIN, GRB>(leds, NUM_LEDS);

  // clear all pixel data
  FastLED.clear(true);

  // Set the light brightness from 0-255.  If too bright, you
  // can permanently damage the arduino, so use a small number to start.
  FastLED.setBrightness(170);
  //FastLED.setMaxPowerInMilliWatts(g_PowerLimit);
FastLED.setMaxPowerInVoltsAndMilliamps(5, 400);
}

void loop() {  
  EVERY_N_MILLISECONDS(100) {
    bluewash();
    //moving();
    abc();
    off();
    //movingtwo();
    four();
    five();
    six();
  }
  
   FastLED.show();
//   delay(500);
//   FastLED.show(false);
}
void bluewash() {
 for (int i = 0; i < section_one; i++) {
    leds[i] = CRGB::Blue;
  }
}  

//void moving() {
//
//  int static pos = 1;
//  int static direction = 1;
//
// 
//   
//  if (pos == NUM_LEDS-3) {
//    direction = -1;
//  } else if (pos == 0) {
//    direction = 1;
//
// 
//    }
//  }
//  pos += direction; {
//  leds[pos] = CRGB::Blue;
// }
//
//void movingtwo() { 
// int static pos1 = 1;
//  int static direction1 = 1; {
//
//}else if (pos1 == 177) {
//      direction1 = -1;
//
//      pos1 += direction1;
//      leds[pos] = CRGB::red

void abc() {

 for (int i = 10; i < section_two; i++) {
   leds[i] = CRGB::Purple;
    
    
  }
}

void off() {

for (int i = 20; i < section_three; i++) {
leds[i] = CRGB::Red;
  }
}

void four() {

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
