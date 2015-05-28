package pset6; 
 
import org.springframework.context.ApplicationContext; 
import org.springframework.context.support.ClassPathXmlApplicationContext; 
import java.util.List;
 
 
/** 
* User: wilsonstls 
* Date: 5/27/15
* 
*/ 
public class MetrolinkCommandLineApp { 
 
 
     public static void main(String[] varArgs) { 
         ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml"); 
         MetrolinkCommandLineApp metrolinkCommandLineApp = 
                 (MetrolinkCommandLineApp) context.getBean("metrolinkCommandLineApp"); 
         metrolinkCommandLineApp.run();
     } 


     private void run() {
         /* list out name, id of all metrolink stops */
         List<Stop> stopsAllStops = metrolinkDao.getStopsAllStops(); 
         for (Stop stop : stopsAllStops) { 
             appOutput.print(String.format("--- %s ---    %s", stop.getStopName(), stop.getStopID()));
         }

         /* list current location for user and minutes until next train arrival */
         List<Stop> stopsMyStop = metrolinkDao.getStopsMyStop();
         for (Stop stop : stopsMyStop) {
             appOutput.print(String.format("Station:  %s ---", stop.getStopName()));
             appOutput.print(String.format("Station ID:  %s ---", stop.getStopID()));
             appOutput.print(String.format("The next train arrives in:  %s  minutes", stop.getStopMinutes()));
         }

     } 

     private MetrolinkDao metrolinkDao; 
     private AppOutput appOutput; 

     public void setMetrolinkDao(MetrolinkDao metrolinkDao) { 
         this.metrolinkDao = metrolinkDao; 
     } 

     public void setAppOutput(AppOutput appOutput) { 
         this.appOutput = appOutput; 
     }

} 
