  
const int analogInPin = A1;  // Analog input pin that the potentiometer is attached to


int sensorValue = 0;        // value read from the pot


void setup() {
  // initialize serial communications at 9600 bps:
  Serial.begin(9600);
}

void loop() {
  // read the analog in value:
  sensorValue = analogRead(analogInPin);
  // map it to the range of the analog out:
 
  // change the analog out value:
 

  // print the results to the serial monitor:
  Serial.print("sensor = ");
  Serial.print(12);
  Serial.println("");
  // wait 2 milliseconds before the next loop
  // for the analog-to-digital converter to settle
  // after the last reading:
  delay(180000);
}
