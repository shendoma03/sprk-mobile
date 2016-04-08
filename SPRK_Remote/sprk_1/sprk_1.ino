int val;
int tempPin = A0;

void setup()
{
Serial.begin(9600);
}
void loop()
{
val = analogRead(tempPin);
float mv = ( val/1024.0)*5000; 
float cel = (mv/10);
    int wtrLevel = analogRead(A1);//
    int smsAnalog = analogRead(A3);//
    int smsDigital = digitalRead(7);//




Serial.print("TEMPRATURE = ");Serial.print(cel);Serial.print("*C");Serial.println();
delay(1000);
smsAnalog = getSoil (smsAnalog);
delay(1000);
wtrLevel = getWtrLevel(wtrLevel);

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
 }else if (wl <= 5){
  return Serial.println(waterVals[2]);
  delay(1000);
 }else  if (wl >=6  && wl < 690 ){
return Serial.println(waterVals[1]);
delay(1000);
 }  
}
 

