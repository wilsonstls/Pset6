package pset6; 

  
/** 
* User: wilsonstls
* Date: 5/27/15
*/ 
 
 
public class Stop { 
 
 
     private String stopName; 
     private String stopID;
     private long stopMinutes;
  
 
     public String getStopName() { 
         return stopName; 
     }
     public void setStopName(String stopName) { 
         this.stopName = stopName; 
     } 
 
 
     public String getStopID() {return stopID;}
     public void setStopID(String stopID) {this.stopID = stopID;}

    public long getStopMinutes() {return stopMinutes;}
    public void setStopMinutes(long stopMinutes) {this.stopMinutes = stopMinutes;}

}
