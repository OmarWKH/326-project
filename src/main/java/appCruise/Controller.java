package appCruise;

class Controller {
  final static int INACTIVE = 0; // cruise controller states
  final static int ACTIVE   = 1;
  final static int CRUISING = 2;
  final static int STANDBY  = 3;
  private int controlState  = INACTIVE; //initial state
  private SpeedControl sc;
  private boolean isfixed;
  volatile private CarSpeed cs;         //interface to control speed of engine

  Controller(CarSpeed cs, CruiseDisplay disp, boolean b)
    {this.cs = cs; sc=new SpeedControl(cs,disp); isfixed=b;}

  synchronized void brake(){
    if (controlState==CRUISING )
      {controlState=STANDBY; }
  }

  synchronized void accelerator(){
    if (controlState==CRUISING )
      {sc.disableControl(); controlState=STANDBY; }
  }

  synchronized void engineOff(){
    if(controlState!=INACTIVE) {
      if (isfixed) sc.disableControl(); 
      controlState=INACTIVE;
    }
  }

  synchronized void engineOn(){
    if(controlState==INACTIVE)
      {sc.clearSpeed(); controlState=ACTIVE;}
  }

  synchronized void on(){
    if(controlState!=INACTIVE && cs.getSpeed() >= 40){
      sc.recordSpeed(); sc.enableControl();
      controlState=CRUISING;
    }
  }

  synchronized void off(){
    if(controlState==CRUISING )
      {sc.disableControl(); controlState=STANDBY;}
    else {
       controlState=INACTIVE;
       sc.disableControl();
    }
  }

  synchronized void resume(){
    if(controlState==STANDBY)
     {sc.enableControl(); controlState=CRUISING;}
  }
}

