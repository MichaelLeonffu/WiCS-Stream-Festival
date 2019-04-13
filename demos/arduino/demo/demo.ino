#include <Servo.h>

//green LED
int greenLed = 8;
unsigned long greenTime = 0;
bool greenOn = false;

//yellow LED
int yellowLed = 4;
int yellowButton = 2;
int yellowButtonPressed = 0;

//servo
Servo myServo;
int myServoPin = 11;
int servoPotMeter = A0;
int servoPotValue;
int servoAngle;

//piezo
int piezoPin = 9;
unsigned long piezoTime = 0;
bool piezoOn = false;
int durationOff = 1000;


void setup() {
  Serial.begin(9600); //Serial monitor

  //pins
  //green LED
  pinMode(greenLed, OUTPUT);
  //yellow LEDd
  pinMode(yellowLed, OUTPUT);
  pinMode(yellowButton, INPUT);
  //servo
  myServo.attach(myServoPin);

}

void loop() {

  //Green LED control: have the green led turn on and off at set intervals
    //time logic, once 300ms passes, then switch the green LED from on to off or off to on
    if(millis() - greenTime > 300){
      greenOn = !greenOn;
      greenTime = millis();
      //Serial.println("Turned greenLed: " + String(greenOn? "On" : "Off"));
    }
  
    //turns on or off the LED
    if(greenOn)
      digitalWrite(greenLed, HIGH);
    else
      digitalWrite(greenLed, LOW);

  //Yellow LED control: if the yellow led button is pressed then light up the yellow LED
    yellowButtonPressed = digitalRead(yellowButton);
  
    if(yellowButtonPressed == HIGH){
      Serial.println("Yellow Button Pressed!");
      digitalWrite(yellowLed, HIGH);
    }else
      digitalWrite(yellowLed, LOW);

  //Servo: control using potentiometer and analogue inputs
    servoPotValue = analogRead(servoPotMeter);

    Serial.println(servoPotValue);
    //maps values from one min to min and max to max
    servoAngle = map(servoPotValue, 0, 1023, 0, 165);
    //send the command to the servo
    myServo.write(servoAngle);

  //Piezo: make sounds!
    //time logic, once durationOffms passes
    if(millis() - piezoTime > durationOff){
      piezoOn = !piezoOn;
      piezoTime = millis();
    }
  
    //turns on or off the LED
    if(piezoOn){
      //the piezo pin, the frequncy, and the duration in ms
      int freq = map(servoPotValue, 0, 1023, 0, 800);
      int duration = 100;
      tone(piezoPin, freq, duration);
    }
    else
      digitalWrite(greenLed, LOW);
    

}
