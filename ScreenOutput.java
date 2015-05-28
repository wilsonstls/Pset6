package pset6.util; 

import pset6.AppOutput; 

 
/** 
* User: mpmenne 
* Date: 7/3/14 
* Time: 2:04 AM 
*/ 
public class ScreenOutput implements AppOutput{ 
     @Override 
     public void print(String output) { 
         System.out.println(output); 
     } 
} 
