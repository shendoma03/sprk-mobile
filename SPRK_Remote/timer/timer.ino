int timer = 0;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
getone();
delay(5000);
timer += 5;
Serial.println(timer);
if (timer == 180){
  Serial.println("It iz 3 minszxc");
  timer = 0;
  }else {
    Serial.println("Not yet!");
    }
}
void getone(){
  Serial.println("Count per 5 sec");
  }
