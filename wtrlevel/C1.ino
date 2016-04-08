
// the setup routine runs once when you press reset:
void setup() {
  // initialize serial communication at 9600 bits per second:
  Serial.begin(9600);
 
}

void loop() {
   int wtrLevel = analogRead(A1);//
    int smsAnalog = analogRead(A3);//
    int smsDigital = digitalRead(7);//
    int tempVal = analogRead(A0);//
     pinMode(sprinkle, OUTPUT); //
    
  
 wtrLevel = getWtrLevel(wtrLevel);
  delay(1000);
  Serial.println(tempVal = getTemp(tempVal));   
  delay(1000); 
  smsAnalog = getSoil (smsAnalog);
 

}


int getTemp (int tv){

  float mv = ( tv/1024.0)*5000; 
    float cel = mv/10;
   // float farh = (cel*9)/5 + 32; // FarenVal
    return cel;
}


 int getSoil (int sv) {
 char* soilVals [] = {"DRY","NORMAL","WET","no soil"};
   if (sv >= 391 && sv <=700)
   { return Serial.println(soilVals[0]);
   } 
   else if (sv>=342 && sv <=390){
  return Serial.println(soilVals[1]);
   
 }
 else if (sv < 340){
return Serial.println(soilVals[2]); 
 }
 else  {
return Serial.println(soilVals[3]);
 }
 }



int getWtrLevel (int wl){
     char* waterVals [] = {"NO water", "LOW" , "HIGH"} ; 
    if (wl >= 700){
 return Serial.println(waterVals[0]); 
 delay(1000); 
 }else if (wl < 12){
  return Serial.println(waterVals[2]);
  delay(1000);
 }else  if (wl >= 16 && wl < 690 ){
return Serial.println(waterVals[1]);
delay(1000);
 }  
}
 
 
     


